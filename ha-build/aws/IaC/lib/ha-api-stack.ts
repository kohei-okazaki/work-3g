import * as path from 'path';
import * as cdk from 'aws-cdk-lib';
import { Duration, RemovalPolicy, Stack, StackProps } from 'aws-cdk-lib';
import * as ec2 from 'aws-cdk-lib/aws-ec2';
import * as ecs from 'aws-cdk-lib/aws-ecs';
import * as iam from 'aws-cdk-lib/aws-iam';
import * as logs from 'aws-cdk-lib/aws-logs';
import * as servicediscovery from 'aws-cdk-lib/aws-servicediscovery';
import { Construct } from 'constructs';

import { HaSharedInfraStack } from './ha-shared-infra-stack';

export interface HaApiStackProps extends StackProps {
  readonly sharedInfraStack: HaSharedInfraStack;
}

export class HaApiStack extends Stack {
  constructor(scope: Construct, id: string, props: HaApiStackProps) {
    super(scope, id, props);

    const sharedInfraStack = props.sharedInfraStack;
    const projectName = String(this.node.tryGetContext('projectName') ?? sharedInfraStack.projectName);
    const apiContainerPort = Number(this.node.tryGetContext('apiContainerPort') ?? 8081);
    const apiDesiredCount = Number(this.node.tryGetContext('apiDesiredCount') ?? 0);
    const apiPublicAllowedCidr = String(this.node.tryGetContext('apiPublicAllowedCidr') ?? '0.0.0.0/0');
    const apiServiceDiscoveryName = String(this.node.tryGetContext('apiServiceDiscoveryName') ?? 'ha-api');

    const healthInfoDashboardUrl = String(this.node.tryGetContext('healthInfoDashboardUrl') ?? '');
    const healthInfoApiUrl = String(this.node.tryGetContext('healthInfoApiUrl') ?? '');
    const rootApiUrl = String(this.node.tryGetContext('rootApiUrl') ?? '');
    const healthInfoTrackApiUrl = String(this.node.tryGetContext('healthInfoTrackApiUrl') ?? '');

    const apiLogGroup = new logs.LogGroup(this, 'ApiLogGroup', {
      logGroupName: `/ecs/${projectName}/ha-api`,
      retention: logs.RetentionDays.ONE_DAY,
      removalPolicy: RemovalPolicy.DESTROY,
    });

    const apiTaskSecurityGroup = new ec2.SecurityGroup(this, 'ApiTaskSecurityGroup', {
      vpc: sharedInfraStack.vpc,
      description: 'Ingress for ha-api tasks from internet and ha-dashboard',
      allowAllOutbound: true,
      securityGroupName: `${projectName}-api-task-sg`,
    });
    if (apiPublicAllowedCidr.length > 0) {
      apiTaskSecurityGroup.addIngressRule(
        ec2.Peer.ipv4(apiPublicAllowedCidr),
        ec2.Port.tcp(apiContainerPort),
        'Allow direct external access to ha-api',
      );
    }
    apiTaskSecurityGroup.addIngressRule(
      sharedInfraStack.internalAppClientSecurityGroup,
      ec2.Port.tcp(apiContainerPort),
      'Allow ha-dashboard to call ha-api',
    );
    const healthInfoApiInternalUrl = `http://${apiServiceDiscoveryName}.${sharedInfraStack.serviceDiscoveryNamespaceName}:${apiContainerPort}/api/`;

    const taskExecutionRole = new iam.Role(this, 'ApiTaskExecutionRole', {
      assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com'),
      managedPolicies: [
        iam.ManagedPolicy.fromAwsManagedPolicyName('service-role/AmazonECSTaskExecutionRolePolicy'),
      ],
    });
    sharedInfraStack.dbAppPasswordParameter.grantRead(taskExecutionRole);

    const taskDefinition = new ecs.FargateTaskDefinition(this, 'ApiTaskDefinition', {
      family: `${projectName}-ha-api`,
      cpu: 256,
      memoryLimitMiB: 512,
      executionRole: taskExecutionRole,
      runtimePlatform: {
        operatingSystemFamily: ecs.OperatingSystemFamily.LINUX,
        cpuArchitecture: ecs.CpuArchitecture.X86_64,
      },
    });

    const appSsmParameterResourceName = `${sharedInfraStack.appSsmParameterPrefix.replace(/^\/+/, '')}/*`;
    taskDefinition.taskRole.addToPrincipalPolicy(new iam.PolicyStatement({
      actions: ['ssm:GetParameter'],
      resources: [
        this.formatArn({
          service: 'ssm',
          resource: 'parameter',
          resourceName: appSsmParameterResourceName,
        }),
      ],
    }));
    taskDefinition.taskRole.addToPrincipalPolicy(new iam.PolicyStatement({
      actions: ['sqs:GetQueueUrl'],
      resources: ['*'],
    }));
    taskDefinition.taskRole.addToPrincipalPolicy(new iam.PolicyStatement({
      actions: ['sqs:SendMessage'],
      resources: [sharedInfraStack.apiLogQueue.queueArn],
    }));

    const dbUrl = `jdbc:mysql://${sharedInfraStack.database.instanceEndpoint.hostname}:${sharedInfraStack.database.instanceEndpoint.port}/${sharedInfraStack.dbName}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo`;
    const apiEnvironment: Record<string, string> = {
      SPRING_PROFILES_ACTIVE: 'dev',
      SPRING_FLYWAY_ENABLED: 'true',
      SPRING_FLYWAY_LOCATIONS: 'classpath:/db/migration',
      DB_URL: dbUrl,
      DB_USER: sharedInfraStack.dbAppUsername,
      SERVER_PORT: String(apiContainerPort),
      API_LOG_QUEUE_NAME: sharedInfraStack.apiLogQueue.queueName,
    };
    if (healthInfoDashboardUrl.length > 0) {
      apiEnvironment.HEALTHINFO_DASHBOARD_URL = healthInfoDashboardUrl;
    }
    if (healthInfoApiUrl.length > 0) {
      apiEnvironment.HEALTHINFO_API_URL = healthInfoApiUrl;
    }
    if (rootApiUrl.length > 0) {
      apiEnvironment.ROOT_API_URL = rootApiUrl;
    }
    if (healthInfoTrackApiUrl.length > 0) {
      apiEnvironment.HEALTHINFO_TRACK_API_URL = healthInfoTrackApiUrl;
    }

    const repositoryRoot = path.resolve(__dirname, '../../../..');
    const apiContainer = taskDefinition.addContainer('ApiContainer', {
      image: ecs.ContainerImage.fromAsset(repositoryRoot, {
        file: 'Dockerfile.ha-api',
      }),
      logging: ecs.LogDrivers.awsLogs({
        streamPrefix: 'ha-api',
        logGroup: apiLogGroup,
      }),
      environment: apiEnvironment,
      secrets: {
        DB_PW: ecs.Secret.fromSsmParameter(sharedInfraStack.dbAppPasswordParameter),
      },
    });
    apiContainer.addPortMappings({ containerPort: apiContainerPort });

    const apiService = new ecs.FargateService(this, 'ApiService', {
      cluster: sharedInfraStack.cluster,
      serviceName: `${projectName}-ha-api-service`,
      taskDefinition,
      desiredCount: apiDesiredCount,
      assignPublicIp: true,
      securityGroups: [apiTaskSecurityGroup, sharedInfraStack.dbClientSecurityGroup],
      vpcSubnets: { subnetType: ec2.SubnetType.PUBLIC },
      minHealthyPercent: 0,
      maxHealthyPercent: 100,
      cloudMapOptions: {
        name: apiServiceDiscoveryName,
        cloudMapNamespace: sharedInfraStack.serviceDiscoveryNamespace,
        dnsRecordType: servicediscovery.DnsRecordType.A,
        dnsTtl: Duration.seconds(30),
      },
    });

    const apiPublicIpCommand = [
      `CLUSTER_NAME=${sharedInfraStack.cluster.clusterName}`,
      `SERVICE_NAME=${apiService.serviceName}`,
      'TASK_ARN=$(aws ecs list-tasks --cluster "$CLUSTER_NAME" --service-name "$SERVICE_NAME" --query \'taskArns[0]\' --output text)',
      'ENI_ID=$(aws ecs describe-tasks --cluster "$CLUSTER_NAME" --tasks "$TASK_ARN" --query \'tasks[0].attachments[0].details[?name==`networkInterfaceId`].value\' --output text)',
      'PUBLIC_IP=$(aws ec2 describe-network-interfaces --network-interface-ids "$ENI_ID" --query \'NetworkInterfaces[0].Association.PublicIp\' --output text)',
      `echo "http://\${PUBLIC_IP}:${apiContainerPort}/api/"`,
    ].join(' && ');

    new cdk.CfnOutput(this, 'ApiServiceName', {
      value: apiService.serviceName,
      exportName: `${this.stackName}-ApiServiceName`,
    });

    new cdk.CfnOutput(this, 'ApiPublicUrlCommand', {
      value: apiPublicIpCommand,
    });

    new cdk.CfnOutput(this, 'ApiPublicAccessPattern', {
      value: `http://<ha-api-task-public-ip>:${apiContainerPort}/api/`,
      exportName: `${this.stackName}-ApiPublicAccessPattern`,
    });

    new cdk.CfnOutput(this, 'ApiInternalBaseUrl', {
      value: healthInfoApiInternalUrl,
      exportName: `${this.stackName}-ApiInternalBaseUrl`,
    });

    new cdk.CfnOutput(this, 'ApiContainerPort', {
      value: String(apiContainerPort),
      exportName: `${this.stackName}-ApiContainerPort`,
    });

    new cdk.CfnOutput(this, 'ApiDesiredCount', {
      value: String(apiDesiredCount),
      exportName: `${this.stackName}-ApiDesiredCount`,
    });

    new cdk.CfnOutput(this, 'ApiPublicAllowedCidr', {
      value: apiPublicAllowedCidr.length > 0 ? apiPublicAllowedCidr : 'disabled',
      exportName: `${this.stackName}-ApiPublicAllowedCidr`,
    });

    new cdk.CfnOutput(this, 'ApiStartCommand', {
      value: `aws ecs update-service --cluster ${sharedInfraStack.cluster.clusterName} --service ${apiService.serviceName} --desired-count 1`,
    });
  }
}
