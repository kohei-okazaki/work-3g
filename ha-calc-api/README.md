# ha-calc-api

健康情報の計算処理を提供するAPIプロジェクト。API GatewayとPythonのLambdaで構成し、実装・CloudFormation定義・ビルド・デプロイ処理をまとめて管理します。

## API
- 基礎健康情報計算API
- カロリー計算API
- 肺活量計算API

## ファイル

- `template.yaml`: API Gateway HTTP API、Lambda、Lambda実行ロール、ルート、統合、Invoke権限を定義するCloudFormationテンプレート。
- `lambda/`: Lambdaソースコード。
- `buildspec.yml`: CodeBuildで `aws cloudformation package` と `aws cloudformation deploy` を実行するビルド仕様。
- `build.sh`: CloudShellやローカルシェルから同じデプロイを実行するための補助スクリプト。

## CodeBuildの設定

CodeBuildプロジェクトはAWSコンソールまたはAWS CLIから手動で作成します。

推奨設定:

- ソース: このリポジトリ。
- ビルド仕様: `ha-calc-api/buildspec.yml`.
- 環境イメージ: AWS CLIを利用できるLinux管理イメージ。
- 特権モード: OFF.
- アーティファクト: なし。

既存のCodeBuildプロジェクトを利用する場合は、AWS側で指定しているビルド仕様ファイルも`ha-calc-api/buildspec.yml`へ変更してください。
CodeBuildプロジェクトの環境変数で`CALC_API_DIR`を上書きしている場合は、値を`ha-calc-api`に合わせてください。

環境変数:

| 名前 | 値 |
| --- | --- |
| `CALC_API_DIR` | `ha-calc-api` |
| `STACK_NAME` | `calc-api` |
| `ARTIFACT_BUCKET` | S3 bucket for CloudFormation package artifacts |
| `ARTIFACT_PREFIX` | `calc-api` |
| `DEPLOY_REGION` | `ap-northeast-1` |
| `STAGE_NAME` | `dev` |

ビルドを開始する前に `ARTIFACT_BUCKET` を作成しておきます。Lambdaのzip成果物は `aws cloudformation package` により、このS3バケットへアップロードされます。


## 手動デプロイ

```bash
cd ha-calc-api
ARTIFACT_BUCKET=<bucket-name> bash ./build.sh
```
