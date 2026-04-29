# calc_api

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
- ビルド仕様: `ha-asset/90_aws/calc_api/buildspec.yml`.
- 環境イメージ: AWS CLIを利用できるLinux管理イメージ。
- 特権モード: OFF.
- アーティファクト: なし。

環境変数:

| 名前 | 値 |
| --- | --- |
| `STACK_NAME` | `calc-api` |
| `ARTIFACT_BUCKET` | S3 bucket for CloudFormation package artifacts |
| `ARTIFACT_PREFIX` | `calc-api` |
| `DEPLOY_REGION` | `ap-northeast-1` |
| `STAGE_NAME` | `dev` |

ビルドを開始する前に `ARTIFACT_BUCKET` を作成しておきます。Lambdaのzip成果物は `aws cloudformation package` により、このS3バケットへアップロードされます。


## 手動デプロイ

```bash
cd ha-asset/90_aws/calc_api
ARTIFACT_BUCKET=<bucket-name> ./build.sh
```
