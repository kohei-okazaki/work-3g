# 健康管理アプリのAWS反映手順（CloudShell）

東京リージョン（`ap-northeast-1`）のCloudShellで実行する。初回は下記の順に進める。

| 基盤 | Terraformの配置 | 管理対象 |
| --- | --- | --- |
| アプリ基盤 | `01_applications/environments/dev` | VPC、ECS、ECR、EC2、RDS、DynamoDB、共用S3、アプリ用IAM・ログ・SQS |
| データ分析基盤 | `02_analytics/environments/dev` | EventBridge、Step Functions、Glue、Athena、SNS、Amazon Q、分析用IAM・ログ・DLQ |

IAMはそれぞれの `modules/iam.tf` に定義する。共用リソースはアプリ基盤で管理し、バケット名などを分析側へ渡す。
リソースや分析処理、RDSアップグレードの詳細は [構成・設定の補足](REFERENCE.md) を参照。

## 事前準備（初回のみ）

- state用S3バケット `healthinfo-tfstate` を東京リージョンに用意する。バージョニング有効、SSE-S3暗号化、パブリックアクセスはすべてブロックする。バージョニングを初めて有効にした場合は15分待つ。
- CloudShell利用者に、Terraformでのリソース操作、ECRへのpush、SSM接続の権限を付与する。stateアクセス権限の例は [こちら](state-backend-policy.example.json)。
- 両基盤の `environments/dev/terraform.tfvars` を用意する。未作成なら各 `terraform.tfvars.example` をコピーする。
- アプリ側は `root_front_url` を既存フロントのURLに変更し、4つの `*_desired_count` は初回は `0` にする。分析側は実際の `slack_team_id` / `slack_channel_id` を指定し、Amazon QとSlack workspaceの認可・チャンネルへの招待を済ませる。
- Systems ManagerのParameter Storeに以下を登録する。名前は既定の `project_name=healthinfo-app`、`app_env=dev` の例。既にある場合は再作成不要。

| パラメータ名 | 種類・用途 |
| --- | --- |
| `/healthinfo-app-dev/db/master/password` | SecureString：RDSマスターパスワード。tfvarsの `db_master_password_parameter_version` と登録バージョンを合わせる |
| `/healthinfo-app-dev/db/app/password` | SecureString：アプリ用DBユーザのパスワード |
| `/healthinfo-app-dev/ha-track/django-secret-key` | SecureString：Djangoの秘密鍵 |
| `/dev/root_jwt_secret` | SecureString：JWT署名用の値 |
| `/dev/crypt_key`、`/dev/crypt_mode`、`/dev/crypt_shift`、`/dev/slack_token`、`/dev/system_mailaddress` | String：各アプリ設定値 |

stateは同じバケット内で分け、S3のロックファイルを使用する。設定済みの `backend.hcl` をそのまま使用する。

| 基盤 | stateの保存先 |
| --- | --- |
| アプリ基盤 | `s3://healthinfo-tfstate/dev/application/terraform.tfstate` |
| データ分析基盤 | `s3://healthinfo-tfstate/dev/analytics/terraform.tfstate` |

## 1. ZIPをアップロードして展開する

手元で `01_applications`、`02_analytics` を**フォルダ名ごと**ZIPにし、CloudShellの「Actions」→「Upload file」で `/home/cloudshell-user` へ1つずつアップロードする。
設定済みの `terraform.tfvars`、`backend.hcl`、存在する場合は `.terraform.lock.hcl` も含める。`.terraform/`、state・バックアップ、planファイル、生成済みの `application.auto.tfvars.json` は除外する。

```bash
cd /home/cloudshell-user
unzip 01_applications.zip
unzip 02_analytics.zip
```

展開後、`/home/cloudshell-user/01_applications/environments/dev/main.tf` と分析側の同じ配置に `main.tf` があることを確認する。既に展開済みのフォルダがある場合は、必要な設定を保管して退避してから展開する。

## 2. CloudShellを準備する

新しいセッションでは、この節を再実行する。大きなファイルは `/tmp/work` に置く。stateはS3に残る。

```bash
export AWS_REGION=ap-northeast-1
export AWS_DEFAULT_REGION="$AWS_REGION"
export TF_WORKSPACE=default
export TF_PLUGIN_CACHE_DIR=/tmp/work/plugin-cache
APP_TF_DIR=/home/cloudshell-user/01_applications/environments/dev
ANALYTICS_TF_DIR=/home/cloudshell-user/02_analytics/environments/dev
mkdir -p "$TF_PLUGIN_CACHE_DIR"

sudo dnf install -y dnf-plugins-core shadow-utils
sudo dnf config-manager --add-repo https://rpm.releases.hashicorp.com/AmazonLinux/hashicorp.repo
sudo dnf install -y terraform-1.16.2
terraform version
aws sts get-caller-identity
docker info
```

Terraformは[HashiCorp公式のAmazon Linux向けリポジトリ](https://developer.hashicorp.com/terraform/install)から `1.16.2` をインストールする。
アプリと分析で `TF_DATA_DIR` を分ける。以下の各手順の `export` を省略しない。
長時間のbuild・apply中もCloudShellを操作し、無操作によるセッション終了を防ぐ。

## 3. アプリ基盤を反映する

```bash
export TF_DATA_DIR=/tmp/work/application
cd "$APP_TF_DIR"
terraform init -backend-config=backend.hcl
terraform validate
terraform plan
terraform apply
```

各コマンドが成功したことを確認してから次へ進む。`plan` の内容を確認して `apply` の確認に `yes` と入力する。

## 4. 共有情報を渡して、データ分析基盤を反映する

アプリ側の出力を分析側の変数ファイルへ書き出す。アプリ基盤を再構築した場合も、この受け渡しを再実行する。

```bash
export TF_DATA_DIR=/tmp/work/application
terraform -chdir="$APP_TF_DIR" output -json analytics_inputs > /tmp/work/application-inputs.json &&
  mv /tmp/work/application-inputs.json "$ANALYTICS_TF_DIR/application.auto.tfvars.json"
```

書き出し成功後、分析側を反映する。生成したJSONの値は `terraform.tfvars` の同じ項目より優先される。

```bash
export TF_DATA_DIR=/tmp/work/analytics
cd "$ANALYTICS_TF_DIR"
terraform init -backend-config=backend.hcl
terraform validate
terraform plan
terraform apply
```

月次CSVの出力は、分析基盤の反映が完了してから開始する。

## 5. ソースをビルドしてECRへ登録する

今回は **CloudShellでgit cloneし、既存Dockerfileでbuildする方法**を使う。
JavaアプリはDockerfile内のMaven 3.9・JDK 25でJARを作るため、手元でのMavenビルドやCloudShell本体へのMaven・JDKインストールは不要。
`ha-track` は同じ操作でPythonのイメージを作る。ECRへ登録するのは、これらのコンテナイメージである。

反映対象のソースは事前にGitへpushする。以下の `master` は反映対象のブランチまたはタグ、`IMAGE_TAG` はアプリ側tfvarsの `image_tag`（未指定時は `dev`）に合わせる。

```bash
(
  set -euo pipefail
  export TF_DATA_DIR=/tmp/work/application
  SOURCE_REF=master
  IMAGE_TAG=dev

  if [ -d /tmp/work/work-3g/.git ]; then
    git -C /tmp/work/work-3g fetch --depth 1 origin "$SOURCE_REF"
    git -C /tmp/work/work-3g checkout --detach FETCH_HEAD
  else
    git clone --depth 1 --branch "$SOURCE_REF" \
      https://github.com/kohei-okazaki/work-3g.git /tmp/work/work-3g
  fi
  cd /tmp/work/work-3g
  git rev-parse --short HEAD

  AWS_ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
  REGISTRY="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
  aws ecr get-login-password --region "$AWS_REGION" |
    docker login --username AWS --password-stdin "$REGISTRY"

  for app in dashboard api root-api track batch; do
    output_name="${app//-/_}_ecr_repository_url"
    repo=$(terraform -chdir="$APP_TF_DIR" output -raw "$output_name")
    docker build --platform linux/amd64 -f "Dockerfile.ha-$app" -t "$repo:$IMAGE_TAG" .
    docker push "$repo:$IMAGE_TAG"
  done
)
```

[CloudShellは1 vCPU・2 GiB RAM](https://docs.aws.amazon.com/cloudshell/latest/userguide/vm-specs.html)のため、上記のとおり順番にbuildする。メモリ不足になる場合はCodeBuildなどのビルド環境へ切り替える。
既存Dockerfileはテストをスキップするため、テスト済みのソースを使用する。

## 6. アプリを起動・更新する

手順5まで完了し、AWS基盤とECRのイメージが用意できた状態で実行する。
初回は「6-1」「6-2」の順に進める。DBとアプリ用ユーザを作成済みの場合は「6-1」を省略する。同じタグでの更新には「6-3」を使用する。

まず、CloudShellのターミナル1でアプリ基盤の作業環境を設定する。

```bash
export AWS_REGION=ap-northeast-1
export AWS_DEFAULT_REGION="$AWS_REGION"
export TF_WORKSPACE=default
export TF_DATA_DIR=/tmp/work/application
export TF_PLUGIN_CACHE_DIR=/tmp/work/plugin-cache
APP_TF_DIR=/home/cloudshell-user/01_applications/environments/dev
mkdir -p "$TF_PLUGIN_CACHE_DIR"
cd "$APP_TF_DIR"
terraform init -backend-config=backend.hcl
```

### 6-1. DBへ接続し、DBとアプリ用ユーザを作成する（初回のみ）

RDSは非公開のため、**CloudShell → SSM → 踏み台EC2 → RDS** の順で接続する。
同じCloudShell環境でターミナルを2つ開く。

#### ターミナル1：接続用トンネルを開く

踏み台がSystems Managerの管理対象としてオンラインで、SSM Agentが `3.1.1374.0` 以上であることが前提。

```bash
if ! command -v session-manager-plugin >/dev/null; then
  case "$(uname -m)" in
    x86_64) PLUGIN_ARCH=linux_64bit ;;
    aarch64) PLUGIN_ARCH=linux_arm64 ;;
  esac
  sudo dnf install -y "https://s3.amazonaws.com/session-manager-downloads/plugin/latest/$PLUGIN_ARCH/session-manager-plugin.rpm"
fi
DB_HOST=$(terraform output -raw db_endpoint_address)
DB_PORT=$(terraform output -raw db_port)
BASTION_ID=$(terraform output -raw bastion_instance_id)

aws ssm start-session --target "$BASTION_ID" \
  --document-name AWS-StartPortForwardingSessionToRemoteHost \
  --parameters "{\"host\":[\"$DB_HOST\"],\"portNumber\":[\"$DB_PORT\"],\"localPortNumber\":[\"13306\"]}"
```

このターミナルは接続したままにする。

#### ターミナル2：MySQLに接続して初期設定する

以下は既定のマスターユーザ名 `healthapp_master` の例。変更している場合はtfvarsの値に合わせる。
パスワードには事前にParameter Storeへ登録したRDSマスターパスワードを入力する。

```bash
docker run --rm -it --network host mysql:9.7.4 \
  mysql --ssl-mode=REQUIRED -h 127.0.0.1 -P 13306 -u healthapp_master -p
```

MySQL内で以下を実行する。DB名・ユーザ名を変更している場合はtfvarsに合わせ、`<APP_DB_PASSWORD>` はParameter Storeのアプリ用パスワードへ置き換える。

```sql
CREATE DATABASE IF NOT EXISTS work3g;
CREATE USER 'app_user'@'%' IDENTIFIED WITH caching_sha2_password BY '<APP_DB_PASSWORD>';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP, INDEX, REFERENCES ON work3g.* TO 'app_user'@'%';
```

マスター接続を `exit` で終了し、アプリ用ユーザでも接続できることを確認する。

```bash
docker run --rm -it --network host mysql:9.7.4 \
  mysql --ssl-mode=REQUIRED -h 127.0.0.1 -P 13306 -u app_user -p work3g
```

確認後はMySQLで `exit`、ターミナル1で `Ctrl+C` を入力してトンネルを閉じる。
以降はターミナル1で作業する。

### 6-2. ECSサービスを起動する

`/home/cloudshell-user/01_applications/environments/dev/terraform.tfvars` の以下の値を変更する。

```hcl
dashboard_desired_count = 1
api_desired_count       = 1
root_api_desired_count  = 1
track_desired_count     = 1
```

変更を反映する。

```bash
cd "$APP_TF_DIR"
terraform validate
terraform plan
terraform apply
```

各コマンドの成功を確認してから次へ進む。`plan` の内容を確認し、`apply` の確認に `yes` と入力する。
`ha-dashboard` の起動時にFlywayがテーブルを作成・更新する。バッチは常駐させず、必要時に単発タスクとして実行する。

### 6-3. 同じイメージタグで更新する場合

稼働中のアプリに**同じタグ**でイメージを上書きした場合は、ECRへのpush後に以下で再デプロイする。

```bash
export TF_DATA_DIR=/tmp/work/application
cd "$APP_TF_DIR"
CLUSTER=$(terraform output -raw cluster_name)
for app in dashboard api root_api track; do
  SERVICE=$(terraform output -raw "${app}_service_name") || break
  aws ecs update-service --cluster "$CLUSTER" --service "$SERVICE" \
    --force-new-deployment --query 'service.serviceName' --output text || break
done
```

## 削除する場合

分析基盤、アプリ基盤の順に削除する。データも削除対象になるため、必要なものを保管してから実行する。state専用バケットは削除されない。
新しいセッションの場合は手順2と、各基盤の `init` を先に実行する。

```bash
export TF_DATA_DIR=/tmp/work/analytics
terraform -chdir="$ANALYTICS_TF_DIR" destroy

export TF_DATA_DIR=/tmp/work/application
terraform -chdir="$APP_TF_DIR" destroy
```

参考：[CloudShellのZIP転送](https://docs.aws.amazon.com/cloudshell/latest/userguide/multiple-files-upload-download.html)、[CloudShellからECRへの登録](https://docs.aws.amazon.com/cloudshell/latest/userguide/tutorial-docker-cli.html)、[SSMのリモートホスト接続](https://docs.aws.amazon.com/systems-manager/latest/userguide/session-manager-working-with-sessions-start.html#sessions-remote-port-forwarding)。
