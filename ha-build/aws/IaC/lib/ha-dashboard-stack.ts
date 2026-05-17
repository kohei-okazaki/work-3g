import * as path from 'path';
import * as cdk from 'aws-cdk-lib';
import { Duration, RemovalPolicy, Stack, StackProps } from 'aws-cdk-lib';
import * as ec2 from 'aws-cdk-lib/aws-ec2';
import * as ecs from 'aws-cdk-lib/aws-ecs';
import * as iam from 'aws-cdk-lib/aws-iam';
import * as logs from 'aws-cdk-lib/aws-logs';
import * as rds from 'aws-cdk-lib/aws-rds';
import * as sqs from 'aws-cdk-lib/aws-sqs';
import * as ssm from 'aws-cdk-lib/aws-ssm';
import { Construct } from 'constructs';

export class HaDashboardStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    // プロジェクト名
    const projectName = String(this.node.tryGetContext('projectName') ?? 'healthinfo-app-dev');
    // DB名
    const dbName = String(this.node.tryGetContext('dbName') ?? 'work3g');
    // マスターユーザ名
    const dbMasterUsername = String(this.node.tryGetContext('dbMasterUsername') ?? 'healthapp_master');
    // RDS MySQLエンジンバージョン
    const dbEngineVersion = String(this.node.tryGetContext('dbEngineVersion') ?? '8.4.8');
    const dbEngineMajorVersion = String(
      this.node.tryGetContext('dbEngineMajorVersion') ?? dbEngineVersion.split('.').slice(0, 2).join('.'),
    );
    // マスターパスワードパラメータ名
    const dbMasterPasswordParameterName = String(
      this.node.tryGetContext('dbMasterPasswordParameterName') ?? `/${projectName}/db/master/password`,
    );
    // マスターパスワードパラメータバージョン
    const dbMasterPasswordParameterVersion = Number(
      this.node.tryGetContext('dbMasterPasswordParameterVersion') ?? 1,
    );

    // アプリ用ユーザ名
    const dbAppUsername = String(this.node.tryGetContext('dbAppUsername') ?? 'app_user');
    // アプリ用パスワードパラメータ名
    const dbAppPasswordParameterName = String(
      this.node.tryGetContext('dbAppPasswordParameterName') ?? `/${projectName}/db/app/password`,
    );
    // アプリ用パスワードパラメータバージョン
    const dbAppPasswordParameterVersion = Number(
      this.node.tryGetContext('dbAppPasswordParameterVersion') ?? 1,
    );
    const dashboardContainerPort = Number(this.node.tryGetContext('dashboardContainerPort') ?? 80);
    const dashboardDesiredCount = Number(this.node.tryGetContext('dashboardDesiredCount') ?? 0);
    const bastionSshAllowedCidr = String(this.node.tryGetContext('bastionSshAllowedCidr') ?? '127.0.0.1/32');
    const bastionInstanceType = String(this.node.tryGetContext('bastionInstanceType') ?? 't3.micro');
    const bastionKeyName = this.node.tryGetContext('bastionKeyName');
    const appSsmParameterPrefix = String(this.node.tryGetContext('appSsmParameterPrefix') ?? '/DEV');
    const apiLogQueueName = String(this.node.tryGetContext('apiLogQueueName') ?? 'dev_api_log.fifo');
    if (!apiLogQueueName.endsWith('.fifo')) {
      throw new Error('apiLogQueueName must end with ".fifo" because ha-dashboard sends FIFO message group IDs.');
    }
    const healthInfoDashboardUrl = String(this.node.tryGetContext('healthInfoDashboardUrl') ?? '');
    const healthInfoApiUrl = String(this.node.tryGetContext('healthInfoApiUrl') ?? '');
    const rootApiUrl = String(this.node.tryGetContext('rootApiUrl') ?? '');
    const healthInfoTrackApiUrl = String(this.node.tryGetContext('healthInfoTrackApiUrl') ?? '');

    const vpc = new ec2.Vpc(this, 'Vpc', {
      vpcName: `${projectName}-vpc`,
      maxAzs: 2,
      natGateways: 0,
      subnetConfiguration: [
        {
          name: 'public-app',
          subnetType: ec2.SubnetType.PUBLIC,
          cidrMask: 24,
        },
        {
          name: 'private-db',
          subnetType: ec2.SubnetType.PRIVATE_ISOLATED,
          cidrMask: 24,
        },
      ],
    });

    const cluster = new ecs.Cluster(this, 'Cluster', {
      vpc,
      clusterName: `${projectName}-cluster`,
    });

    const dashboardIngressSecurityGroup = new ec2.SecurityGroup(this, 'DashboardIngressSecurityGroup', {
      vpc,
      description: 'Internet ingress for ha-dashboard without ALB',
      allowAllOutbound: true,
      securityGroupName: `${projectName}-dashboard-web-sg`,
    });
    dashboardIngressSecurityGroup.addIngressRule(
      ec2.Peer.anyIpv4(),
      ec2.Port.tcp(dashboardContainerPort),
      'Allow direct web access to ha-dashboard',
    );

    const sharedDbClientSecurityGroup = new ec2.SecurityGroup(this, 'SharedDbClientSecurityGroup', {
      vpc,
      description: 'Attach this SG to ha-dashboard now and ha-api later to reach the same RDS',
      allowAllOutbound: true,
      securityGroupName: `${projectName}-shared-db-client-sg`,
    });

    const dbSecurityGroup = new ec2.SecurityGroup(this, 'DbSecurityGroup', {
      vpc,
      description: 'RDS MySQL SG',
      allowAllOutbound: true,
      securityGroupName: `${projectName}-db-sg`,
    });
    dbSecurityGroup.addIngressRule(
      sharedDbClientSecurityGroup,
      ec2.Port.tcp(3306),
      'Allow MySQL from shared DB client SG',
    );

    const bastionSecurityGroup = new ec2.SecurityGroup(this, 'BastionSecurityGroup', {
      vpc,
      description: 'SSH bastion host SG for manual RDS setup',
      allowAllOutbound: true,
      securityGroupName: `${projectName}-bastion-sg`,
    });
    bastionSecurityGroup.addIngressRule(
      ec2.Peer.ipv4(bastionSshAllowedCidr),
      ec2.Port.tcp(22),
      'Allow SSH to bastion host',
    );
    dbSecurityGroup.addIngressRule(
      bastionSecurityGroup,
      ec2.Port.tcp(3306),
      'Allow MySQL from bastion host',
    );

    // Low-cost secret storage: use an existing SSM Parameter Store Standard SecureString.
    // This stack does not create Secrets Manager secrets or customer-managed KMS keys.
    const dbAppPasswordParameter = ssm.StringParameter.fromSecureStringParameterAttributes(
      this,
      'DbAppPasswordParameter',
      {
        parameterName: dbAppPasswordParameterName,
        version: dbAppPasswordParameterVersion,
      },
    );

    const dbSubnetGroup = new rds.SubnetGroup(this, 'DbSubnetGroup', {
      vpc,
      description: 'Subnet group for private RDS MySQL',
      vpcSubnets: { subnetType: ec2.SubnetType.PRIVATE_ISOLATED },
      subnetGroupName: `${projectName}-db-subnet-group`,
    });

    const database = new rds.DatabaseInstance(this, 'Database', {
      vpc,
      vpcSubnets: { subnetType: ec2.SubnetType.PRIVATE_ISOLATED },
      subnetGroup: dbSubnetGroup,
      engine: rds.DatabaseInstanceEngine.mysql({
        version: rds.MysqlEngineVersion.of(dbEngineVersion, dbEngineMajorVersion),
      }),
      instanceType: ec2.InstanceType.of(ec2.InstanceClass.BURSTABLE3, ec2.InstanceSize.MICRO),
      allocatedStorage: 20,
      storageType: rds.StorageType.GP3,
      multiAz: false,
      credentials: rds.Credentials.fromPassword(
        dbMasterUsername,
        cdk.SecretValue.ssmSecure(dbMasterPasswordParameterName, String(dbMasterPasswordParameterVersion)),
      ),
      securityGroups: [dbSecurityGroup],
      publiclyAccessible: false,
      backupRetention: Duration.days(0),
      deleteAutomatedBackups: true,
      deletionProtection: false,
      removalPolicy: RemovalPolicy.DESTROY,
      autoMinorVersionUpgrade: true,
    });

    const bastionRole = new iam.Role(this, 'BastionRole', {
      assumedBy: new iam.ServicePrincipal('ec2.amazonaws.com'),
      managedPolicies: [
        iam.ManagedPolicy.fromAwsManagedPolicyName('AmazonSSMManagedInstanceCore'),
      ],
    });

    const bastionKeyNameProps = bastionKeyName !== undefined && String(bastionKeyName).length > 0
      ? { keyName: String(bastionKeyName) }
      : {};
    const bastionHost = new ec2.Instance(this, 'BastionHost', {
      vpc,
      vpcSubnets: { subnetType: ec2.SubnetType.PUBLIC },
      instanceName: `${projectName}-bastion`,
      instanceType: new ec2.InstanceType(bastionInstanceType),
      machineImage: ec2.MachineImage.latestAmazonLinux2({
        cpuType: ec2.AmazonLinuxCpuType.X86_64,
      }),
      securityGroup: bastionSecurityGroup,
      role: bastionRole,
      requireImdsv2: true,
      ...bastionKeyNameProps,
    });
    bastionHost.userData.addCommands(
      'set -eux',
      'yum install -y mariadb',
    );

    const logGroup = new logs.LogGroup(this, 'DashboardLogGroup', {
      logGroupName: `/ecs/${projectName}/ha-dashboard`,
      retention: logs.RetentionDays.ONE_DAY,
      removalPolicy: RemovalPolicy.DESTROY,
    });

    const apiLogQueue = new sqs.Queue(this, 'ApiLogQueue', {
      queueName: apiLogQueueName,
      fifo: true,
      retentionPeriod: Duration.days(1),
      removalPolicy: RemovalPolicy.DESTROY,
    });

    const taskExecutionRole = new iam.Role(this, 'TaskExecutionRole', {
      assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com'),
      managedPolicies: [
        iam.ManagedPolicy.fromAwsManagedPolicyName('service-role/AmazonECSTaskExecutionRolePolicy'),
      ],
    });
    dbAppPasswordParameter.grantRead(taskExecutionRole);

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
    const appSsmParameterResourceName = `${appSsmParameterPrefix.replace(/^\/+/, '')}/*`;
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
      resources: [apiLogQueue.queueArn],
    }));

    const repositoryRoot = path.resolve(__dirname, '../../../..');
    const dashboardEnvironment: Record<string, string> = {
      // Spring Boot設定
      SPRING_PROFILES_ACTIVE: 'dev',
      SPRING_FLYWAY_ENABLED: 'true',
      SPRING_FLYWAY_LOCATIONS: 'classpath:/db/migration',
      // DB接続情報
      DB_URL: `jdbc:mysql://${database.instanceEndpoint.hostname}:${database.instanceEndpoint.port}/${dbName}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo`,
      DB_USER: dbAppUsername,
      SERVER_PORT: String(dashboardContainerPort),
      API_LOG_QUEUE_NAME: apiLogQueue.queueName,
    };
    if (healthInfoDashboardUrl.length > 0) {
      dashboardEnvironment.HEALTHINFO_DASHBOARD_URL = healthInfoDashboardUrl;
    }
    if (healthInfoApiUrl.length > 0) {
      dashboardEnvironment.HEALTHINFO_API_URL = healthInfoApiUrl;
    }
    if (rootApiUrl.length > 0) {
      dashboardEnvironment.ROOT_API_URL = rootApiUrl;
    }
    if (healthInfoTrackApiUrl.length > 0) {
      dashboardEnvironment.HEALTHINFO_TRACK_API_URL = healthInfoTrackApiUrl;
    }

    // 健康管理ダッシュボードのコンテナ定義
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
        DB_PW: ecs.Secret.fromSsmParameter(dbAppPasswordParameter),
      },
    });
    dashboardContainer.addPortMappings({ containerPort: dashboardContainerPort });

    const dashboardService = new ecs.FargateService(this, 'DashboardService', {
      cluster,
      serviceName: `${projectName}-ha-dashboard-service`,
      taskDefinition,
      desiredCount: dashboardDesiredCount,
      assignPublicIp: true,
      securityGroups: [dashboardIngressSecurityGroup, sharedDbClientSecurityGroup],
      vpcSubnets: { subnetType: ec2.SubnetType.PUBLIC },
      minHealthyPercent: 0,
      maxHealthyPercent: 100,
    });

    new cdk.CfnOutput(this, 'VpcId', {
      value: vpc.vpcId,
      exportName: `${this.stackName}-VpcId`,
    });

    new cdk.CfnOutput(this, 'ClusterName', {
      value: cluster.clusterName,
      exportName: `${this.stackName}-ClusterName`,
    });

    new cdk.CfnOutput(this, 'DashboardServiceName', {
      value: dashboardService.serviceName,
      exportName: `${this.stackName}-DashboardServiceName`,
    });

    new cdk.CfnOutput(this, 'SharedDbClientSecurityGroupId', {
      value: sharedDbClientSecurityGroup.securityGroupId,
      exportName: `${this.stackName}-SharedDbClientSecurityGroupId`,
    });

    new cdk.CfnOutput(this, 'BastionInstanceId', {
      value: bastionHost.instanceId,
      exportName: `${this.stackName}-BastionInstanceId`,
    });

    new cdk.CfnOutput(this, 'BastionPublicIp', {
      value: bastionHost.instancePublicIp,
      exportName: `${this.stackName}-BastionPublicIp`,
    });

    new cdk.CfnOutput(this, 'BastionSecurityGroupId', {
      value: bastionSecurityGroup.securityGroupId,
      exportName: `${this.stackName}-BastionSecurityGroupId`,
    });

    new cdk.CfnOutput(this, 'BastionSsmCommand', {
      value: `aws ssm start-session --target ${bastionHost.instanceId}`,
    });

    new cdk.CfnOutput(this, 'BastionSshCommand', {
      value: `ssh ec2-user@${bastionHost.instancePublicIp}`,
    });

    new cdk.CfnOutput(this, 'DbEndpointAddress', {
      value: database.instanceEndpoint.hostname,
      exportName: `${this.stackName}-DbEndpointAddress`,
    });

    new cdk.CfnOutput(this, 'DbPort', {
      value: database.instanceEndpoint.port.toString(),
      exportName: `${this.stackName}-DbPort`,
    });

    new cdk.CfnOutput(this, 'DbName', {
      value: dbName,
      exportName: `${this.stackName}-DbName`,
    });

    new cdk.CfnOutput(this, 'DbEngineVersion', {
      value: dbEngineVersion,
      exportName: `${this.stackName}-DbEngineVersion`,
    });

    new cdk.CfnOutput(this, 'DbMasterUsername', {
      value: dbMasterUsername,
      exportName: `${this.stackName}-DbMasterUsername`,
    });

    new cdk.CfnOutput(this, 'DbMasterPasswordParameterName', {
      value: dbMasterPasswordParameterName,
      exportName: `${this.stackName}-DbMasterPasswordParameterName`,
    });

    new cdk.CfnOutput(this, 'DbAppUsername', {
      value: dbAppUsername,
      exportName: `${this.stackName}-DbAppUsername`,
    });

    new cdk.CfnOutput(this, 'DbAppPasswordParameterName', {
      value: dbAppPasswordParameterName,
      exportName: `${this.stackName}-DbAppPasswordParameterName`,
    });

    new cdk.CfnOutput(this, 'AppSsmParameterPrefix', {
      value: appSsmParameterPrefix,
      exportName: `${this.stackName}-AppSsmParameterPrefix`,
    });

    new cdk.CfnOutput(this, 'ApiLogQueueName', {
      value: apiLogQueue.queueName,
      exportName: `${this.stackName}-ApiLogQueueName`,
    });

    new cdk.CfnOutput(this, 'ApiLogQueueUrl', {
      value: apiLogQueue.queueUrl,
      exportName: `${this.stackName}-ApiLogQueueUrl`,
    });

    new cdk.CfnOutput(this, 'SecretStoragePolicy', {
      value: 'Use existing SSM Parameter Store Standard SecureString parameters; no Secrets Manager secret is created.',
    });

    new cdk.CfnOutput(this, 'DashboardContainerPort', {
      value: String(dashboardContainerPort),
      exportName: `${this.stackName}-DashboardContainerPort`,
    });

    new cdk.CfnOutput(this, 'DashboardDesiredCount', {
      value: String(dashboardDesiredCount),
      exportName: `${this.stackName}-DashboardDesiredCount`,
    });

    new cdk.CfnOutput(this, 'DbCreateDatabaseCommand', {
      value: `mysql -h ${database.instanceEndpoint.hostname} -P ${database.instanceEndpoint.port} -u ${dbMasterUsername} -p < CREATE_DATABASE.sql`,
    });

    new cdk.CfnOutput(this, 'DbConnectCommand', {
      value: `mysql -h ${database.instanceEndpoint.hostname} -P ${database.instanceEndpoint.port} -u ${dbMasterUsername} -p`,
    });

    new cdk.CfnOutput(this, 'DashboardStartCommand', {
      value: `aws ecs update-service --cluster ${cluster.clusterName} --service ${dashboardService.serviceName} --desired-count 1`,
    });
  }
}
