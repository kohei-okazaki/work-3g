# Terraform for healthinfo application services

作成する主なリソース:

- VPC / public app subnet x 2 / private DB subnet x 2 / Internet Gateway
- ECS Cluster
- Cloud Map private DNS namespace
- `ha-dashboard` ECS Fargate service / task definition / CloudWatch Logs
- `ha-api` ECS Fargate service / task definition / CloudWatch Logs / Cloud Map service
- `ha-root` API ECS Fargate service / task definition / CloudWatch Logs / Cloud Map service
- `ha-track` ECS Fargate service / task definition / CloudWatch Logs / Cloud Map service
- `ha-batch` ECS Fargate task definition / CloudWatch Logs
- RDS MySQL `8.4.8`
- DynamoDB 
- SQS FIFO queue
- RDS初期設定用のEC2踏み台
- ECR repositories for `ha-dashboard`, `ha-api`, `ha-root` API, `ha-track`, and `ha-batch` images

`ha-root` / `ha-api` / `ha-dashboard` は同じRDS databaseを同じアプリ用DBユーザで参照。

## 注意

- このTerraform構成でECRリポジトリを作成し、Docker imageは手順に沿って `docker build` / `docker push`
- `ha-root/front` のS3 static websiteは既存管理のままとし、このTerraformでは作成・変更対象外
- TerraformでRDS master passwordをSSM SecureStringから読むため、値はTerraform stateにsensitive値として保持
- `terraform.tfstate` と `*.tfvars` はGit管理対象外。環境ごとの `environments/<環境名>` 配下に配置

## ディレクトリ構成

```text
ha-build/aws/IaC/
├─ modules/              # AWSリソースの共通定義
│  ├─ network.tf
│  ├─ security_groups.tf
│  ├─ iam.tf
│  ├─ rds.tf
│  ├─ dynamodb.tf
│  ├─ sqs.tf
│  ├─ ecs.tf
│  ├─ locals.tf
│  ├─ variables.tf
│  └─ outputs.tf
├─ environments/
│  └─ dev/               # dev環境のTerraform実行ルート
│     ├─ main.tf
│     ├─ versions.tf
│     ├─ providers.tf
│     ├─ variables.tf
│     ├─ outputs.tf
│     ├─ backend.hcl
│     └─ terraform.tfvars
└─ README.md
```

`modules` は単独で実行せず、`environments/dev` からmoduleとして呼び出す。

## 前提

- Windows PCにAWS CLI / Dockerが入っていること
- Git BashからAWS CLIの認証が通っていること
- RDSへの接続のため、SSM Session Manager pluginが入っていること
- `Dockerfile.ha-dashboard`、`Dockerfile.ha-api`、`Dockerfile.ha-root-api`、`Dockerfile.ha-track` がリポジトリ直下にあること
- RDS master passwordとapp user passwordのSSM SecureStringを事前に作っておくこと
- `ha-root/front` の既存S3サイトURLを `root_front_url` として渡せること
- `ha-root` API が利用する既存アプリデータ用S3 bucket名を `app_data_bucket_name` として渡せること

## Terraformのインストール

```bash
# インストール
winget install --id Hashicorp.Terraform --exact --source winget

# ターミナル再起動
terraform version

# TerraformはAWS CLIと同じ認証情報を利用。反映先が意図したAWSアカウントであることを確認。
aws sts get-caller-identity

# 複数のAWS CLIプロファイルがある場合は、使用するプロファイルを設定してから確認。
export AWS_PROFILE="プロファイル名"
aws sts get-caller-identity
```

## 変数例

```bash
export MSYS_NO_PATHCONV=1

AWS_ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
PROJECT_NAME=ha-project
APP_ENV=dev
RESOURCE_PREFIX="${PROJECT_NAME}-${APP_ENV}"
```

DynamoDBの設定:

- dev: `health_info_dev`、PITRなし
- stg: `health_info_stg`、PITRなし
- prd: `health_info_prd`、PITRあり
- パーティションキー: `seq_user_id`（Number）
- ソートキー: `created_at_epoch`（Number）
- キャパシティーモード: On-demand
- 削除保護: 無効

dev/stg/prdのDynamoDBテーブルは、他のTerraform管理リソースと同様に
`terraform destroy` でテーブルと登録データが削除される。

## SSM SecureString作成（最初のみでOK）

```bash
# ${MASTER_PASSWORD}は正しい値に置換。
aws ssm put-parameter \
  --name "/${RESOURCE_PREFIX}/db/master/password" \
  --type "SecureString" \
  --tier "Standard" \
  --value "${MASTER_PASSWORD}" \
  --overwrite

# ${APP_DB_PASSWORD}は正しい値に置換。
aws ssm put-parameter \
  --name "/${RESOURCE_PREFIX}/db/app/password" \
  --type "SecureString" \
  --tier "Standard" \
  --value "${APP_DB_PASSWORD}" \
  --overwrite

# ${DJANGO_SECRET_KEY}には十分に長いランダム値を指定。
aws ssm put-parameter \
  --name "/${RESOURCE_PREFIX}/ha-track/django-secret-key" \
  --type "SecureString" \
  --tier "Standard" \
  --value "${DJANGO_SECRET_KEY}" \
  --overwrite
```

## アプリ起動に必要なSSM Parameter作成（最初のみでOK）

```bash
aws ssm put-parameter --name "/${APP_ENV}/crypt_key" --type "String" --value "${VALUE}" --overwrite
aws ssm put-parameter --name "/${APP_ENV}/crypt_mode" --type "String" --value "${VALUE}" --overwrite
aws ssm put-parameter --name "/${APP_ENV}/crypt_shift" --type "String" --value "${VALUE}" --overwrite
aws ssm put-parameter --name "/${APP_ENV}/root_jwt_secret" --type "SecureString" --value "${VALUE}" --overwrite
aws ssm put-parameter --name "/${APP_ENV}/slack_token" --type "String" --value "${VALUE}" --overwrite
aws ssm put-parameter --name "/${APP_ENV}/system_mailaddress" --type "String" --value "${VALUE}" --overwrite
```

## Terraformの初期化・整形・検証

```bash
cd ha-build/aws/IaC/environments/dev
terraform init -backend-config=backend.hcl
terraform fmt -recursive ../..
terraform validate
```

## リソース確認(terraform plan)

最初は `dashboard_desired_count=0` / `api_desired_count=0` / `root_api_desired_count=0` /
`track_desired_count=0` のまま作成。
目的はDBとアプリ用ユーザの初期設定、Docker image pushの完了前にECS taskが起動して失敗することの防止。

```bash
terraform plan
```

## リソース反映(terraform apply)

```bash
terraform apply
```

## Docker imageをECRへpush

Terraform apply後にECR repository URLを取得。
同じタグへ新しいimageをpushしてタグが外れた古いimageは、pushから1日を超えるとECR Lifecycle Policyの削除対象。
現在タグが付いている最新imageは削除対象外。削除条件を満たした後、実際の削除まで最大24時間程度。

```bash
DASHBOARD_REPO=$(terraform output -raw dashboard_ecr_repository_url)
API_REPO=$(terraform output -raw api_ecr_repository_url)
ROOT_API_REPO=$(terraform output -raw root_api_ecr_repository_url)
TRACK_REPO=$(terraform output -raw track_ecr_repository_url)
BATCH_REPO=$(terraform output -raw batch_ecr_repository_url)

aws ecr get-login-password --region ap-northeast-1 \
  | docker login --username AWS --password-stdin "${AWS_ACCOUNT_ID}.dkr.ecr.ap-northeast-1.amazonaws.com"
```

リポジトリ直下をbuild contextにしてpush。

```bash
cd ../../../../..

docker build -f Dockerfile.ha-dashboard -t "${DASHBOARD_REPO}:dev" .
docker push "${DASHBOARD_REPO}:dev"

docker build -f Dockerfile.ha-api -t "${API_REPO}:dev" .
docker push "${API_REPO}:dev"

docker build -f Dockerfile.ha-root-api -t "${ROOT_API_REPO}:dev" .
docker push "${ROOT_API_REPO}:dev"

docker build -f Dockerfile.ha-track -t "${TRACK_REPO}:dev" .
docker push "${TRACK_REPO}:dev"

docker build -f Dockerfile.ha-batch -t "${BATCH_REPO}:dev" .
docker push "${BATCH_REPO}:dev"

cd ha-build/aws/IaC/environments/dev
```

## ha-batch起動

`ha-batch` は常駐サービスではなく、ECS Fargateの単発タスクとして起動する。
基本コマンドは次で確認できる。

```bash
terraform output -raw batch_run_command
```

出力コマンドの `JOB_NAME` を実行対象のSpring Batch Job名へ変更する。
日付が必要なJobは、container overrideの `command` 配列へ `d=YYYYMMDD` または
`m=YYYYMM` を追加する。

主なJob名:

- `healthCheckBatchJob`
- `healthInfoFileRegistBatchJob`
- `monthlyHealthInfoSummaryBatchJob`
- `healthInfoMigrateBatchJob`
- `awsSqsImportBatchJob`
- `dataPurgeBatchJob`
- `dailyHealthInfoJob`
- `dailyUserJob`
- `dailyApiLogJob`
- `dailyBatchLogJob`

## RDS接続・初期設定

RDSはprivate DB subnetにあるため、ローカルPCから直接接続不可。
Git BashからSSM Session Managerのポートフォワードを起動し、踏み台EC2経由で接続。

### 1. Terraform Outputの取得

```bash
DbEndpointAddress=$(terraform output -raw db_endpoint_address)
DbPort=$(terraform output -raw db_port)
DbMasterUsername=$(terraform output -raw db_master_username)
BastionInstanceId=$(terraform output -raw bastion_instance_id)
```

### 2. ポートフォワードの起動

別のGit Bashターミナルで実行。
このコマンドの実行中のみ、ローカルPCの `127.0.0.1:13306` からRDSへ接続可能。

```bash
export MSYS_NO_PATHCONV=1

aws ssm start-session \
  --target ${BastionInstanceId} \
  --document-name AWS-StartPortForwardingSessionToRemoteHost \
  --parameters "{\"host\":[\"${DbEndpointAddress}\"],\"portNumber\":[\"${DbPort}\"],\"localPortNumber\":[\"13306\"]}"
```

### 3. データベースの作成

ポートフォワード中に、元のGit Bashターミナルから実行。
ローカルPCへのMySQL clientのインストールは不要。DockerのMySQL clientを利用。
初回実行時は `mysql:8.0` imageをダウンロード。

```bash
# SSMから値を設定
read -s -p "RDS master password: " DB_MASTER_PASSWORD
echo

docker run --rm -i \
  -e MYSQL_PWD="${DB_MASTER_PASSWORD}" \
  mysql:8.0 \
  mysql -h host.docker.internal -P 13306 -u ${DbMasterUsername} < ../../../../../ha-asset/02_db/others/CREATE_DATABASE.sql

unset DB_MASTER_PASSWORD
```

### 4. アプリ用DBユーザの作成

RDS master userでMySQLへ接続。

```bash
winpty docker run --rm -it mysql:8.0 \
  mysql -h host.docker.internal -P 13306 -u ${DbMasterUsername} -p
```

MySQL接続後、アプリ用DBユーザを作成。
`<APP_DB_PASSWORD>`には `db_app_password_parameter_name` のSSM SecureStringと同じ値を指定。
FlywayのDDLに外部キー作成が含まれるため、`REFERENCES`権限も付与。

```sql
CREATE USER 'app_user'@'%' IDENTIFIED BY '<APP_DB_PASSWORD>';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP, INDEX, REFERENCES ON work3g.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
```

### 5. アプリ用DBユーザの接続確認

ユーザ作成後に実行。

```bash
winpty docker run --rm -it mysql:8.0 \
  mysql -h host.docker.internal -P 13306 -u app_user -p work3g
```

## ha-api / ha-dashboard / ha-root API / ha-track起動

DB初期設定とECR pushの完了後、desired countを1へ変更。

```bash
aws ecs update-service \
  --cluster ${RESOURCE_PREFIX}-cluster \
  --service ${RESOURCE_PREFIX}-ha-root-api-service \
  --desired-count 1

aws ecs update-service \
  --cluster ${RESOURCE_PREFIX}-cluster \
  --service ${RESOURCE_PREFIX}-ha-api-service \
  --desired-count 1

aws ecs update-service \
  --cluster ${RESOURCE_PREFIX}-cluster \
  --service ${RESOURCE_PREFIX}-ha-dashboard-service \
  --desired-count 1

aws ecs update-service \
  --cluster ${RESOURCE_PREFIX}-cluster \
  --service ${RESOURCE_PREFIX}-ha-track-service \
  --desired-count 1
```

`ha-dashboard` 起動時にFlywayでDDL / DMLを実行。
`ha-track` はTask Roleを使い、対象環境の `health_info_dev` または `health_info_prd`
に対する `dynamodb:PutItem` のみ許可。

## アクセス方法

AWS上の`ha-dashboard`は80番ポートで起動するため、URLのポート指定は不要。

```bash
CLUSTER_NAME=${RESOURCE_PREFIX}-cluster
SERVICE_NAME=${RESOURCE_PREFIX}-ha-dashboard-service

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

DASHBOARD_PUBLIC_IP=$(aws ec2 describe-network-interfaces \
  --network-interface-ids "$ENI_ID" \
  --query 'NetworkInterfaces[0].Association.PublicIp' \
  --output text)

echo "http://${DASHBOARD_PUBLIC_IP}/login"
```

AWS上の`ha-api`も80番ポートで起動し、URLのポート指定なしで直接アクセス可能。
`ha-dashboard`からはCloud Map private DNSの `http://ha-api.<namespace>/api/` を使用。

```bash
CLUSTER_NAME=${RESOURCE_PREFIX}-cluster
SERVICE_NAME=${RESOURCE_PREFIX}-ha-api-service

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

API_PUBLIC_IP=$(aws ec2 describe-network-interfaces \
  --network-interface-ids "$ENI_ID" \
  --query 'NetworkInterfaces[0].Association.PublicIp' \
  --output text)

echo "http://${API_PUBLIC_IP}/api/"
curl -i "http://${API_PUBLIC_IP}/api/healthcheck"
```

AWS上の`ha-root` APIも80番ポートで起動し、URLのポート指定なしで直接アクセス可能。
`ha-dashboard` / `ha-api`からはCloud Map private DNSの `http://ha-root.<namespace>/api/root/` を使用。

```bash
CLUSTER_NAME=${RESOURCE_PREFIX}-cluster
SERVICE_NAME=${RESOURCE_PREFIX}-ha-root-api-service

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

ROOT_API_PUBLIC_IP=$(aws ec2 describe-network-interfaces \
  --network-interface-ids "$ENI_ID" \
  --query 'NetworkInterfaces[0].Association.PublicIp' \
  --output text)

echo "http://${ROOT_API_PUBLIC_IP}/api/root/"
curl -i "http://${ROOT_API_PUBLIC_IP}/api/root/healthcheck"
```

`ha-track` はCloud Map private DNSの
`http://ha-track.<namespace>:8086/api/` を内部通信に使用。
外部から直接アクセスする場合は、`environments/dev/terraform.tfvars` で以下を設定する。

```hcl
track_public_allowed_cidr = "接続元IP/32"
track_django_allowed_hosts = "*"
```

外部公開を有効にした場合のPublic IP取得コマンドは以下。

```bash
terraform output -raw track_public_url_command
```

## ログ確認

```bash
aws logs tail /ecs/${RESOURCE_PREFIX}/ha-dashboard --follow
aws logs tail /ecs/${RESOURCE_PREFIX}/ha-api --follow
aws logs tail /ecs/${RESOURCE_PREFIX}/ha-root-api --follow
aws logs tail /ecs/${RESOURCE_PREFIX}/ha-track --follow
```

## 削除

```bash
terraform destroy
```
