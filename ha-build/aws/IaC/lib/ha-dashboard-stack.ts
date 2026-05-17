import * as path from 'path';
import * as cdk from 'aws-cdk-lib';
import { RemovalPolicy, Stack, StackProps } from 'aws-cdk-lib';
import * as ec2 from 'aws-cdk-lib/aws-ec2';
import * as ecs from 'aws-cdk-lib/aws-ecs';
import * as iam from 'aws-cdk-lib/aws-iam';
import * as logs from 'aws-cdk-lib/aws-logs';
import { Construct } from 'constructs';

import { HaSharedInfraStack } from './ha-shared-infra-stack';

export interface HaDashboardStackProps extends StackProps {
  readonly sharedInfraStack: HaSharedInfraStack;
}

export class HaDashboardStack extends Stack {
  public readonly dashboardIngressSecurityGroup: ec2.SecurityGroup;

  constructor(scope: Construct, id: string, props: HaDashboardStackProps) {
    super(scope, id, props);

    const sharedInfraStack = props.sharedInfraStack;
    const projectName = String(this.node.tryGetContext('projectName') ?? sharedInfraStack.projectName);
    const dashboardContainerPort = Number(this.node.tryGetContext('dashboardContainerPort') ?? 80);
    const dashboardDesiredCount = Number(this.node.tryGetContext('dashboardDesiredCount') ?? 0);
    const apiContainerPort = Number(this.node.tryGetContext('apiContainerPort') ?? 8081);
    const apiServiceDiscoveryName = String(this.node.tryGetContext('apiServiceDiscoveryName') ?? 'ha-api');
    const healthInfoDashboardUrl = String(this.node.tryGetContext('healthInfoDashboardUrl') ?? '');
    const healthInfoApiUrl = String(this.node.tryGetContext('healthInfoApiUrl') ?? '');
    const rootApiUrl = String(this.node.tryGetContext('rootApiUrl') ?? '');
    const healthInfoTrackApiUrl = String(this.node.tryGetContext('healthInfoTrackApiUrl') ?? '');

    const healthInfoApiInternalUrl = `http://${apiServiceDiscoveryName}.${sharedInfraStack.serviceDiscoveryNamespaceName}:${apiContainerPort}/api/`;
    const dashboardHealthInfoApiUrl = healthInfoApiUrl.length > 0 ? healthInfoApiUrl : healthInfoApiInternalUrl;

    const dashboardIngressSecurityGroup = new ec2.SecurityGroup(this, 'DashboardIngressSecurityGroup', {
      vpc: sharedInfraStack.vpc,
      description: 'Internet ingress for ha-dashboard without ALB',
      allowAllOutbound: true,
      securityGroupName: `${projectName}-dashboard-web-sg`,
    });
    dashboardIngressSecurityGroup.addIngressRule(
      ec2.Peer.anyIpv4(),
      ec2.Port.tcp(dashboardContainerPort),
      'Allow direct web access to ha-dashboard',
    );
    this.dashboardIngressSecurityGroup = dashboardIngressSecurityGroup;

    const logGroup = new logs.LogGroup(this, 'DashboardLogGroup', {
      logGroupName: `/ecs/${projectName}/ha-dashboard`,
      retention: logs.RetentionDays.ONE_DAY,
      removalPolicy: RemovalPolicy.DESTROY,
    });

    const taskExecutionRole = new iam.Role(this, 'DashboardTaskExecutionRole', {
      assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com'),
      managedPolicies: [
        iam.ManagedPolicy.fromAwsManagedPolicyName('service-role/AmazonECSTaskExecutionRolePolicy'),
      ],
    });
    sharedInfraStack.dbAppPasswordParameter.grantRead(taskExecutionRole);

    const taskDefinition = new ecs.FargateTaskDefinition(this, 'DashboardTaskDefinition', {
      family: `${projectName}-ha-dashboard`,
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
    const dashboardEnvironment: Record<string, string> = {
      SPRING_PROFILES_ACTIVE: 'dev',
      SPRING_FLYWAY_ENABLED: 'true',
      SPRING_FLYWAY_LOCATIONS: 'classpath:/db/migration',
      DB_URL: dbUrl,
      DB_USER: sharedInfraStack.dbAppUsername,
      SERVER_PORT: String(dashboardContainerPort),
      API_LOG_QUEUE_NAME: sharedInfraStack.apiLogQueue.queueName,
      HEALTHINFO_API_URL: dashboardHealthInfoApiUrl,
    };
    if (healthInfoDashboardUrl.length > 0) {
      dashboardEnvironment.HEALTHINFO_DASHBOARD_URL = healthInfoDashboardUrl;
    }
    if (rootApiUrl.length > 0) {
      dashboardEnvironment.ROOT_API_URL = rootApiUrl;
    }
    if (healthInfoTrackApiUrl.length > 0) {
      dashboardEnvironment.HEALTHINFO_TRACK_API_URL = healthInfoTrackApiUrl;
    }

    const repositoryRoot = path.resolve(__dirname, '../../../..');
    const dashboardContainer = taskDefinition.addContainer('DashboardContainer', {
      image: ecs.ContainerImage.fromAsset(repositoryRoot, {
        file: 'Dockerfile.ha-dashboard',
      }),
      logging: ecs.LogDrivers.awsLogs({
        streamPrefix: 'ha-dashboard',
        logGroup,
      }),
      environment: dashboardEnvironment,
      secrets: {
        DB_PW: ecs.Secret.fromSsmParameter(sharedInfraStack.dbAppPasswordParameter),
      },
    });
    dashboardContainer.addPortMappings({ containerPort: dashboardContainerPort });

    const dashboardService = new ecs.FargateService(this, 'DashboardService', {
      cluster: sharedInfraStack.cluster,
      serviceName: `${projectName}-ha-dashboard-service`,
      taskDefinition,
      desiredCount: dashboardDesiredCount,
      assignPublicIp: true,
      securityGroups: [
        dashboardIngressSecurityGroup,
        sharedInfraStack.dbClientSecurityGroup,
        sharedInfraStack.internalAppClientSecurityGroup,
      ],
      vpcSubnets: { subnetType: ec2.SubnetType.PUBLIC },
      minHealthyPercent: 0,
      maxHealthyPercent: 100,
    });

    new cdk.CfnOutput(this, 'DashboardServiceName', {
      value: dashboardService.serviceName,
      exportName: `${this.stackName}-DashboardServiceName`,
    });

    new cdk.CfnOutput(this, 'DashboardHealthInfoApiUrl', {
      value: dashboardHealthInfoApiUrl,
      exportName: `${this.stackName}-DashboardHealthInfoApiUrl`,
    });

    new cdk.CfnOutput(this, 'DashboardContainerPort', {
      value: String(dashboardContainerPort),
      exportName: `${this.stackName}-DashboardContainerPort`,
    });

    new cdk.CfnOutput(this, 'DashboardDesiredCount', {
      value: String(dashboardDesiredCount),
      exportName: `${this.stackName}-DashboardDesiredCount`,
    });

    new cdk.CfnOutput(this, 'DashboardStartCommand', {
      value: `aws ecs update-service --cluster ${sharedInfraStack.cluster.clusterName} --service ${dashboardService.serviceName} --desired-count 1`,
    });
  }
}
