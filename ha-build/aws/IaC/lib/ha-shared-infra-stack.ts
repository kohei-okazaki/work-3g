import * as cdk from 'aws-cdk-lib';
import { Duration, RemovalPolicy, Stack, StackProps } from 'aws-cdk-lib';
import * as ec2 from 'aws-cdk-lib/aws-ec2';
import * as ecs from 'aws-cdk-lib/aws-ecs';
import * as iam from 'aws-cdk-lib/aws-iam';
import * as rds from 'aws-cdk-lib/aws-rds';
import * as servicediscovery from 'aws-cdk-lib/aws-servicediscovery';
import * as sqs from 'aws-cdk-lib/aws-sqs';
import * as ssm from 'aws-cdk-lib/aws-ssm';
import { Construct } from 'constructs';

export class HaSharedInfraStack extends Stack {
  public readonly projectName: string;
  public readonly vpc: ec2.Vpc;
  public readonly cluster: ecs.Cluster;
  public readonly serviceDiscoveryNamespace: servicediscovery.PrivateDnsNamespace;
  public readonly serviceDiscoveryNamespaceName: string;
  public readonly dbClientSecurityGroup: ec2.SecurityGroup;
  public readonly internalAppClientSecurityGroup: ec2.SecurityGroup;
  public readonly dbSecurityGroup: ec2.SecurityGroup;
  public readonly database: rds.DatabaseInstance;
  public readonly dbName: string;
  public readonly dbAppUsername: string;
  public readonly dbAppPasswordParameter: ssm.IStringParameter;
  public readonly apiLogQueue: sqs.Queue;
  public readonly appSsmParameterPrefix: string;

  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const projectName = String(this.node.tryGetContext('projectName') ?? 'healthinfo-app-dev');
    const projectDnsLabel = projectName
      .toLowerCase()
      .replace(/[^a-z0-9-]/g, '-')
      .replace(/^-+|-+$/g, '') || 'healthinfo-app';
    const dbName = String(this.node.tryGetContext('dbName') ?? 'work3g');
    const dbMasterUsername = String(this.node.tryGetContext('dbMasterUsername') ?? 'healthapp_master');
    const dbEngineVersion = String(this.node.tryGetContext('dbEngineVersion') ?? '8.4.8');
    const dbEngineMajorVersion = String(
      this.node.tryGetContext('dbEngineMajorVersion') ?? dbEngineVersion.split('.').slice(0, 2).join('.'),
    );
    const dbMasterPasswordParameterName = String(
      this.node.tryGetContext('dbMasterPasswordParameterName') ?? `/${projectName}/db/master/password`,
    );
    const dbMasterPasswordParameterVersion = Number(
      this.node.tryGetContext('dbMasterPasswordParameterVersion') ?? 1,
    );
    const dbAppUsername = String(this.node.tryGetContext('dbAppUsername') ?? 'app_user');
    const dbAppPasswordParameterName = String(
      this.node.tryGetContext('dbAppPasswordParameterName') ?? `/${projectName}/db/app/password`,
    );
    const dbAppPasswordParameterVersion = Number(
      this.node.tryGetContext('dbAppPasswordParameterVersion') ?? 1,
    );
    const bastionSshAllowedCidr = String(this.node.tryGetContext('bastionSshAllowedCidr') ?? '127.0.0.1/32');
    const bastionInstanceType = String(this.node.tryGetContext('bastionInstanceType') ?? 't3.micro');
    const bastionKeyName = this.node.tryGetContext('bastionKeyName');
    const appSsmParameterPrefix = String(this.node.tryGetContext('appSsmParameterPrefix') ?? '/dev');
    const apiLogQueueName = String(this.node.tryGetContext('apiLogQueueName') ?? 'dev_api_log.fifo');
    if (!apiLogQueueName.endsWith('.fifo')) {
      throw new Error('apiLogQueueName must end with ".fifo" because app services send FIFO message group IDs.');
    }
    const serviceDiscoveryNamespaceName = String(
      this.node.tryGetContext('serviceDiscoveryNamespaceName') ?? `${projectDnsLabel}.local`,
    );

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

    const serviceDiscoveryNamespace = new servicediscovery.PrivateDnsNamespace(this, 'ServiceDiscoveryNamespace', {
      vpc,
      name: serviceDiscoveryNamespaceName,
    });

    const dbClientSecurityGroup = new ec2.SecurityGroup(this, 'SharedDbClientSecurityGroup', {
      vpc,
      description: 'Attach this SG to app services to reach RDS',
      allowAllOutbound: true,
      securityGroupName: `${projectName}-shared-db-client-sg`,
    });

    const internalAppClientSecurityGroup = new ec2.SecurityGroup(this, 'InternalAppClientSecurityGroup', {
      vpc,
      description: 'Attach this SG to app services that call internal app endpoints',
      allowAllOutbound: true,
      securityGroupName: `${projectName}-internal-app-client-sg`,
    });

    const dbSecurityGroup = new ec2.SecurityGroup(this, 'DbSecurityGroup', {
      vpc,
      description: 'RDS MySQL SG',
      allowAllOutbound: true,
      securityGroupName: `${projectName}-db-sg`,
    });
    dbSecurityGroup.addIngressRule(
      dbClientSecurityGroup,
      ec2.Port.tcp(3306),
      'Allow MySQL from app DB client SG',
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

    const apiLogQueue = new sqs.Queue(this, 'ApiLogQueue', {
      queueName: apiLogQueueName,
      fifo: true,
      retentionPeriod: Duration.days(1),
      removalPolicy: RemovalPolicy.DESTROY,
    });

    this.projectName = projectName;
    this.vpc = vpc;
    this.cluster = cluster;
    this.serviceDiscoveryNamespace = serviceDiscoveryNamespace;
    this.serviceDiscoveryNamespaceName = serviceDiscoveryNamespaceName;
    this.dbClientSecurityGroup = dbClientSecurityGroup;
    this.internalAppClientSecurityGroup = internalAppClientSecurityGroup;
    this.dbSecurityGroup = dbSecurityGroup;
    this.database = database;
    this.dbName = dbName;
    this.dbAppUsername = dbAppUsername;
    this.dbAppPasswordParameter = dbAppPasswordParameter;
    this.apiLogQueue = apiLogQueue;
    this.appSsmParameterPrefix = appSsmParameterPrefix;

    new cdk.CfnOutput(this, 'VpcId', {
      value: vpc.vpcId,
      exportName: `${this.stackName}-VpcId`,
    });

    new cdk.CfnOutput(this, 'ClusterName', {
      value: cluster.clusterName,
      exportName: `${this.stackName}-ClusterName`,
    });

    new cdk.CfnOutput(this, 'ServiceDiscoveryNamespaceName', {
      value: serviceDiscoveryNamespaceName,
      exportName: `${this.stackName}-ServiceDiscoveryNamespaceName`,
    });

    new cdk.CfnOutput(this, 'SharedDbClientSecurityGroupId', {
      value: dbClientSecurityGroup.securityGroupId,
      exportName: `${this.stackName}-SharedDbClientSecurityGroupId`,
    });

    new cdk.CfnOutput(this, 'InternalAppClientSecurityGroupId', {
      value: internalAppClientSecurityGroup.securityGroupId,
      exportName: `${this.stackName}-InternalAppClientSecurityGroupId`,
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

    new cdk.CfnOutput(this, 'DbCreateDatabaseCommand', {
      value: `mysql -h ${database.instanceEndpoint.hostname} -P ${database.instanceEndpoint.port} -u ${dbMasterUsername} -p < CREATE_DATABASE.sql`,
    });

    new cdk.CfnOutput(this, 'DbConnectCommand', {
      value: `mysql -h ${database.instanceEndpoint.hostname} -P ${database.instanceEndpoint.port} -u ${dbMasterUsername} -p`,
    });
  }
}
