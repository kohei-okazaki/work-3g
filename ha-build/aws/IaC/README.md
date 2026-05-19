# CDK for ha-dashboard and ha-api

## この版でコストを落としている点
- `ha-dashboard` には **ALB を作りません**
- `ha-api` も **ALB を作らず**、Fargate task の public IP へ直接アクセスします
- **NAT Gateway を作りません**
- **ECS Container Insights を有効化しません**
- **CloudWatch Logs の保持期間を 1 日** にしています
- **RDS は Single-AZ / db.t3.micro / 20GB** の最小寄り構成です
- DB等の秘密情報は**SSM SecureString** に保存しています
- `ha-dashboard` から `ha-api` への呼び出しは private DNS で解決し、ALB は使いません

## 作成物
CDK stack は以下の 3 つに分けています。

- `HaSharedInfraStack`: VPC、ECS Cluster、Cloud Map private DNS namespace、MySQL RDS、SQS FIFO、RDS 初期設定用 EC2 踏み台、共通 Security Group
- `HaDashboardStack`: `ha-dashboard` 用 ECS Fargate Service / Task Definition / Log Group / Security Group
- `HaApiStack`: `ha-api` 用 ECS Fargate Service / Task Definition / Log Group / Security Group

`ha-dashboard` と `ha-api` は同じ RDS / 同じアプリ用 DB ユーザを参照します。RDS への 3306 許可は共有 DB クライアント Security Group で管理します。

## 前提
- この CDK を **`ha-build/aws/IaC`** に配置すること
- リポジトリ直下に `Dockerfile.ha-dashboard` があること
- リポジトリ直下に `Dockerfile.ha-api` があること
- Docker が起動していること
- Git BashがローカルPCにインストールされていること
- Windows PC に AWS CLI をインストールし、Git Bash から AWS CLI の認証が通っていること
- SSM Session Manager で踏み台へ接続する場合は、ローカルに Session Manager plugin が入っていること

配置イメージ:

```text
work-3g/
├─ Dockerfile.ha-api
├─ Dockerfile.ha-dashboard
├─ ha-api/
├─ ha-dashboard/
├─ ha-pom/
└─ ha-build/
   └─ aws/
      └─ IaC/
```

## Git Bash 用の変数例
以降のコマンド例では、CDK の `projectName` を `ha-project-dev`、アプリ実行環境を `dev` としています。
`dev` はアプリ内の `system.env=dev` から SSM パラメータ名 `/dev/...` を参照するために使います。

```bash
export MSYS_NO_PATHCONV=1

AWS_ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
PROJECT_NAME=ha-project-dev
APP_ENV=dev
YOUR_GLOBAL_IP=$(curl -s https://checkip.amazonaws.com | tr -d '\n')
```

Git Bash は `/ha-project-dev/...` のような値を Windows パスへ自動変換することがあります。
SSM パラメータ名が `C:/Program Files/Git/...` に変わらないように、同じターミナルで `export MSYS_NO_PATHCONV=1` を先に実行してください。

## 先に DB パスワード用 SecureString を作成
RDS の master パスワードと、`ha-dashboard` / `ha-api` が同一ユーザで利用するアプリ用パスワードは別の SystemsManager SecureString にします。
低コストにするため、Secrets Manager は使わず、SSM Parameter Store の Standard tier を明示します。

```bash
export MSYS_NO_PATHCONV=1

aws ssm put-parameter \
  --name "/${PROJECT_NAME}/db/master/password" \
  --type "SecureString" \
  --tier "Standard" \
  --value "${masterパスワード}" \
  --overwrite

aws ssm put-parameter \
  --name "/${PROJECT_NAME}/db/app/password" \
  --type "SecureString" \
  --tier "Standard" \
  --value "${CREATE_USER.sql の app_user パスワードと同じ値}" \
  --overwrite
```
`--key-id` は指定せず、AWS 管理キー `alias/aws/ssm` を使います。カスタマー管理 KMS キーを作らないため、KMS キーの月額料金を避けられます。
※値を更新した場合は Parameter Store のバージョンが増えるので、`dbMasterPasswordParameterVersion` / `dbAppPasswordParameterVersion` も合わせて更新すること。

## アプリ起動に必要なパラメータ を事前登録
```bash
export MSYS_NO_PATHCONV=1

aws ssm put-parameter \
  --name "/${APP_ENV}/crypt_key" \
  --type "String" \
  --value "${値を指定}" \
  --overwrite
aws ssm put-parameter \
  --name "/${APP_ENV}/crypt_mode" \
  --type "String" \
  --value "${値を指定}" \
  --overwrite
aws ssm put-parameter \
  --name "/${APP_ENV}/crypt_shift" \
  --type "String" \
  --value "${値を指定}" \
  --overwrite
aws ssm put-parameter \
  --name "/${APP_ENV}/root_jwt_secret" \
  --type "String" \
  --value "${値を指定}" \
  --overwrite
aws ssm put-parameter \
  --name "/${APP_ENV}/slack_token" \
  --type "String" \
  --value "${値を指定}" \
  --overwrite
aws ssm put-parameter \
  --name "/${APP_ENV}/system_mailaddress" \
  --type "String" \
  --value "${値を指定}" \
  --overwrite
```

## インストール
```bash
cd ha-build/aws/IaC
npm install
```

## CDK のビルド確認
```bash
cd ha-build/aws/IaC
# TypeScriptをJavaScriptへコンパイル
npm run build

# CDKコードからCloudFormationテンプレートを生成
npx cdk synth HaSharedInfraStack
npx cdk synth HaDashboardStack
npx cdk synth HaApiStack
```

## 初回 bootstrap
```bash
cd ha-build/aws/IaC
export MSYS_NO_PATHCONV=1

npx cdk bootstrap aws://${AWS_ACCOUNT_ID}/ap-northeast-1
```

## デプロイ
RDS MySQL の利用可能なバージョンは時期やリージョンで変わります。必要に応じて以下で確認してください。

```bash
aws rds describe-db-engine-versions \
  --engine mysql \
  --region ap-northeast-1 \
  --query "DBEngineVersions[].EngineVersion" \
  --output text
```

まず共有インフラを作成します。

```bash
cd ha-build/aws/IaC
export MSYS_NO_PATHCONV=1

npx cdk deploy HaSharedInfraStack \
  -c projectName=${PROJECT_NAME} \
  -c dbName=work3g \
  -c dbEngineVersion=8.4.8 \
  -c dbMasterUsername=healthapp_master \
  -c dbMasterPasswordParameterName=/${PROJECT_NAME}/db/master/password \
  -c dbMasterPasswordParameterVersion=1 \
  -c dbAppUsername=app_user \
  -c dbAppPasswordParameterName=/${PROJECT_NAME}/db/app/password \
  -c dbAppPasswordParameterVersion=1 \
  -c apiLogQueueName=dev_api_log.fifo \
  -c appSsmParameterPrefix=/${APP_ENV} \
  -c bastionSshAllowedCidr=${YOUR_GLOBAL_IP}/32
```

次に `ha-dashboard` を作成します。

```bash
cd ha-build/aws/IaC
export MSYS_NO_PATHCONV=1

npx cdk deploy HaDashboardStack \
  -c projectName=${PROJECT_NAME} \
  -c dbName=work3g \
  -c dbAppUsername=app_user \
  -c dbAppPasswordParameterName=/${PROJECT_NAME}/db/app/password \
  -c dbAppPasswordParameterVersion=1 \
  -c apiContainerPort=8081 \
  -c apiServiceDiscoveryName=ha-api \
  -c dashboardContainerPort=80 \
  -c dashboardDesiredCount=0 \
  -c appSsmParameterPrefix=/${APP_ENV}
```

次に `ha-api` を作成します。外部からは Fargate task の public IP に直接アクセスします。

```bash
cd ha-build/aws/IaC
export MSYS_NO_PATHCONV=1

npx cdk deploy HaApiStack \
  -c projectName=${PROJECT_NAME} \
  -c dbName=work3g \
  -c dbAppUsername=app_user \
  -c dbAppPasswordParameterName=/${PROJECT_NAME}/db/app/password \
  -c dbAppPasswordParameterVersion=1 \
  -c apiContainerPort=8081 \
  -c apiDesiredCount=0 \
  -c apiPublicAllowedCidr=0.0.0.0/0 \
  -c apiServiceDiscoveryName=ha-api \
  -c appSsmParameterPrefix=/${APP_ENV}
```

`dashboardDesiredCount=0` / `apiDesiredCount=0` で作成すると、アプリ用 DB / ユーザを作る前に ECS タスクが起動して失敗することを避けられます。
`dashboardContainerPort=80` にすることで、AWS dev 環境の `ha-dashboard` はポート指定なしの `http://<PublicIP>/login` でアクセスします。local 環境のようにサーバごとに 8080 / 8081 / 8082 を分ける前提ではありません。
`apiContainerPort=8081` は `ha-api` の起動ポートです。`apiPublicAllowedCidr` は `ha-api` task の public IP へ直接アクセスできる CIDR で、空文字にすると外部からの direct access を作りません。

各 stack は同じ CDK app 内で共有スタックを参照するため、`projectName`、`dbName`、`dbAppUsername`、`dbAppPasswordParameterName`、`appSsmParameterPrefix` などは stack 間で同じ値にしてください。毎回 `-c` で渡す代わりに `cdk.json` の `context` に固定しても構いません。

`apiLogQueueName` は `ha-dashboard` / `ha-api` が API 通信ログ送信に使う SQS FIFO キュー名です。CDK がこのキューを作成し、ECS タスクロールに `sqs:GetQueueUrl` / `sqs:SendMessage` を付与します。FIFO キューのため、名前は `.fifo` で終わる必要があります。

`ha-dashboard` から `ha-api` への呼び出しは、未指定の場合 `http://ha-api.<private namespace>:8081/api/` の private DNS を使います。別の API を使う場合だけ `healthInfoApiUrl` でベースURLを上書きします。

```bash
# 例
-c healthInfoApiUrl=http://example.com/api/
```

同名の SQS キューを手動作成済みの場合、CDK が同名キューを新規作成できずデプロイに失敗します。その場合は手動作成したキューを削除してから `npx cdk deploy` してください。

## RDS への接続方法
RDS は private isolated subnet に作成するため、ローカルPCから直接接続できません。
Git Bash から SSM Session Manager のポートフォワードを起動し、踏み台EC2経由で接続します。

まず CloudFormation Output から接続に必要な値を取得します。

```bash
export MSYS_NO_PATHCONV=1
STACK_NAME=HaSharedInfraStack

DbEndpointAddress=$(aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query "Stacks[0].Outputs[?OutputKey=='DbEndpointAddress'].OutputValue" \
  --output text)
DbPort=$(aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query "Stacks[0].Outputs[?OutputKey=='DbPort'].OutputValue" \
  --output text)
DbMasterUsername=$(aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query "Stacks[0].Outputs[?OutputKey=='DbMasterUsername'].OutputValue" \
  --output text)
BastionInstanceId=$(aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query "Stacks[0].Outputs[?OutputKey=='BastionInstanceId'].OutputValue" \
  --output text)
```

別の Git Bash ターミナルでポートフォワードを起動します。このコマンドを実行している間だけ、ローカルPCの `127.0.0.1:13306` がRDSへつながります。

```bash
export MSYS_NO_PATHCONV=1

aws ssm start-session \
  --target ${BastionInstanceId} \
  --document-name AWS-StartPortForwardingSessionToRemoteHost \
  --parameters "{\"host\":[\"${DbEndpointAddress}\"],\"portNumber\":[\"${DbPort}\"],\"localPortNumber\":[\"13306\"]}"
```

ポートフォワード中に、もう1つの Git Bash ターミナルからMySQLへ接続します。

ローカルPCへ MySQL client はインストールせず、Docker の MySQL client を使います。
初回実行時は `mysql:8.0` イメージのダウンロードが走ります。

```bash
winpty docker run --rm -it mysql:8.0 \
  mysql -h host.docker.internal -P 13306 -u ${DbMasterUsername} -p
```

アプリ用ユーザで接続する場合は以下です。

```bash
winpty docker run --rm -it mysql:8.0 \
  mysql -h host.docker.internal -P 13306 -u app_user -p work3g
```

## RDS 初期設定
CDK デプロイ後、Output の `DbEndpointAddress`、`DbPort`、`BastionInstanceId`、`BastionPublicIp` を確認します。

```bash
STACK_NAME=HaSharedInfraStack

aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query 'Stacks[0].Outputs[].[OutputKey,OutputValue]' \
  --output table

DbEndpointAddress=$(aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query "Stacks[0].Outputs[?OutputKey=='DbEndpointAddress'].OutputValue" \
  --output text)
DbPort=$(aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query "Stacks[0].Outputs[?OutputKey=='DbPort'].OutputValue" \
  --output text)
DbMasterUsername=$(aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query "Stacks[0].Outputs[?OutputKey=='DbMasterUsername'].OutputValue" \
  --output text)
BastionInstanceId=$(aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query "Stacks[0].Outputs[?OutputKey=='BastionInstanceId'].OutputValue" \
  --output text)
BastionPublicIp=$(aws cloudformation describe-stacks \
  --stack-name ${STACK_NAME} \
  --query "Stacks[0].Outputs[?OutputKey=='BastionPublicIp'].OutputValue" \
  --output text)
```

SSM Session Manager で踏み台経由のポートフォワードを使う場合は、別ターミナルで以下を実行します。

```bash
aws ssm start-session \
  --target ${BastionInstanceId} \
  --document-name AWS-StartPortForwardingSessionToRemoteHost \
  --parameters "{\"host\":[\"${DbEndpointAddress}\"],\"portNumber\":[\"${DbPort}\"],\"localPortNumber\":[\"13306\"]}"
```

ポートフォワード中に、ローカルPCからRDSへSQLを流します。

```bash
read -s -p "RDS master password: " DB_MASTER_PASSWORD
echo

docker run --rm -i \
  -e MYSQL_PWD="${DB_MASTER_PASSWORD}" \
  mysql:8.0 \
  mysql -h host.docker.internal -P 13306 -u ${DbMasterUsername} < ../../../ha-asset/02_db/others/CREATE_DATABASE.sql

unset DB_MASTER_PASSWORD

winpty docker run --rm -it mysql:8.0 \
  mysql -h host.docker.internal -P 13306 -u ${DbMasterUsername} -p
```

MySQL に接続したら、アプリ用DBとアプリ用ユーザを作成します。

```sql
CREATE DATABASE IF NOT EXISTS work3g;
CREATE USER 'app_user'@'%' IDENTIFIED BY '3w4tamudnxgr4';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP, INDEX, REFERENCES ON work3g.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
```

`CREATE_USER.sql` には local 用と RDS 用の定義が同居しているため、RDS では `app_user`@`%` の `CREATE USER` / `GRANT` 部分を実行してください。アプリ用パスワードは `dbAppPasswordParameterName` の SecureString と同じ値にします。Flyway のDDLには外部キー作成が含まれるため、`REFERENCES` 権限も必要です。

## ha-dashboard / ha-api 起動
RDS 初期設定後に ECS Service の desired count を 1 に上げると、`ha-dashboard` と `ha-api` のFargateタスクが起動します。
Flyway は `ha-dashboard` 側だけで有効化しており、アプリ起動時に `ha-db/src/main/resources/db/migration` の DDL / DML を適用します。`ha-api` は同じ RDS / 同じ `app_user` を参照します。

```bash
aws ecs update-service \
  --cluster ${PROJECT_NAME}-cluster \
  --service ${PROJECT_NAME}-ha-api-service \
  --desired-count 1

aws ecs update-service \
  --cluster ${PROJECT_NAME}-cluster \
  --service ${PROJECT_NAME}-ha-dashboard-service \
  --desired-count 1
```

## デプロイ後に ha-dashboard へアクセスする方法

```bash
CLUSTER_NAME=${PROJECT_NAME}-cluster
SERVICE_NAME=${PROJECT_NAME}-ha-dashboard-service

TASK_ARN=$(aws ecs list-tasks \
  --cluster "$CLUSTER_NAME" \
  --service-name "$SERVICE_NAME" \
  --query 'taskArns[0]' \
  --output text)

ENI_ID=$(aws ecs describe-tasks \
  --cluster "$CLUSTER_NAME" \
  --tasks "$TASK_ARN" \
  --query 'tasks[0].attachments[0].details[?name==`networkInterfaceId`].value' \
  --output text)

PUBLIC_IP=$(aws ec2 describe-network-interfaces \
  --network-interface-ids "$ENI_ID" \
  --query 'NetworkInterfaces[0].Association.PublicIp' \
  --output text)

echo "http://${PUBLIC_IP}/login"
```

## デプロイ後に ha-api へアクセスする方法
`ha-dashboard` からは Cloud Map の private DNS で `ha-api` を呼びます。CloudFormation Output の `DashboardHealthInfoApiUrl` が実際に dashboard へ渡されるURLです。
外部から `ha-api` 単体を呼ぶ場合は、ECS task の public IP を取得して直接呼びます。task の再起動や再デプロイで public IP は変わります。

```bash
CLUSTER_NAME=${PROJECT_NAME}-cluster
SERVICE_NAME=${PROJECT_NAME}-ha-api-service

TASK_ARN=$(aws ecs list-tasks \
  --cluster "$CLUSTER_NAME" \
  --service-name "$SERVICE_NAME" \
  --query 'taskArns[0]' \
  --output text)

ENI_ID=$(aws ecs describe-tasks \
  --cluster "$CLUSTER_NAME" \
  --tasks "$TASK_ARN" \
  --query 'tasks[0].attachments[0].details[?name==`networkInterfaceId`].value' \
  --output text)

PUBLIC_IP=$(aws ec2 describe-network-interfaces \
  --network-interface-ids "$ENI_ID" \
  --query 'NetworkInterfaces[0].Association.PublicIp' \
  --output text)

echo "http://${PUBLIC_IP}:8081/api/"
curl -i "http://${PUBLIC_IP}:8081/api/healthcheck"
```

タスク起動状況を確認します。

```bash
aws ecs describe-services \
  --cluster ${PROJECT_NAME}-cluster \
  --services ${PROJECT_NAME}-ha-dashboard-service ${PROJECT_NAME}-ha-api-service \
  --query 'services[].{service:serviceName,desired:desiredCount,running:runningCount,pending:pendingCount,deployments:deployments[].rolloutState}' \
  --output table
```

CloudWatch Logs で起動ログとFlyway実行結果を確認します。`--follow` を使う場合は、それぞれ別ターミナルで実行してください。

```bash
aws logs tail /ecs/${PROJECT_NAME}/ha-dashboard --follow
aws logs tail /ecs/${PROJECT_NAME}/ha-api --follow
```

再起動したい場合は、ECS Service の新規デプロイを強制します。

```bash
aws ecs update-service \
  --cluster ${PROJECT_NAME}-cluster \
  --service ${PROJECT_NAME}-ha-api-service \
  --force-new-deployment

aws ecs update-service \
  --cluster ${PROJECT_NAME}-cluster \
  --service ${PROJECT_NAME}-ha-dashboard-service \
  --force-new-deployment
```

## 削除
```bash
cd ha-build/aws/IaC
export MSYS_NO_PATHCONV=1

npx cdk destroy HaApiStack
npx cdk destroy HaDashboardStack
npx cdk destroy HaSharedInfraStack
```



## 補足
- Docker build context は CDK stack 内で **リポジトリ直下** を向くようにしてあります。
- Fargate タスクは NAT Gateway を使わないように public subnet に配置しています。
- `ha-api` は ALB を作成せず、ECS タスク自体への public IP direct access で外部公開します。固定URLが必要な場合は別途 ALB / Route 53 などが必要です。
- VPC / ECS Cluster / Cloud Map private DNS namespace / RDS / SQS / 踏み台は `HaSharedInfraStack` に集約しています。
- `HaDashboardStack` は `ha-dashboard` の ECS 関連、`HaApiStack` は `ha-api` の ECS 関連だけを持ちます。
- RDS は private isolated subnet に配置しています。
- RDS 作成時にはアプリ用 database は作成せず、`CREATE_DATABASE.sql` を踏み台から実行する想定です。
- `ha-dashboard` / `ha-api` の ECS タスクには master パスワードを渡さず、`DB_USER` / `DB_PW` には同じアプリ用ユーザの値だけを渡します。
- `ha-dashboard` / `ha-api` から RDS への 3306 許可は、両方の ECS Service に共有 DB クライアント Security Group を付与して実現します。
- `ha-dashboard` の `HEALTHINFO_API_URL` は、未指定なら Cloud Map private DNS の `ha-api` に向けます。Cloud Map namespace は `HaSharedInfraStack`、`ha-api` service registration は `HaApiStack` 側で作成します。
- `ha-api` は Flyway を無効化しています。DDL / DML の適用は `ha-dashboard` 側に寄せています。
- ECS タスクロールには `appSsmParameterPrefix` 配下の `ssm:GetParameter` だけを許可します。デフォルトは `/dev/*` です。
- 踏み台 EC2 の IAM ロールには DB パスワード用 SSM パラメータの読取権限を付与しません。RDS 初期設定時の master パスワードは手元で管理し、MySQL 接続時に入力します。
- CDK は既存の SSM Parameter Store Standard SecureString を参照するだけで、Secrets Manager の secret やカスタマー管理 KMS キーは作成しません。
- 開発用途を想定して、RDS は `RemovalPolicy.DESTROY`、`deletionProtection: false` にしています。消したくない場合は変更してください。
- ECS に SSM パラメータを注入したあとでパスワードを更新しても、既存タスクには自動反映されません。新しいタスクを起動してください。
