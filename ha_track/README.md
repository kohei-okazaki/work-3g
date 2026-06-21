# ha-track

## 概要
健康管理アプリで登録した健康情報を連携、蓄積するプロジェクト

## API一覧
- 健康情報登録API：
    - 概要：ユーザの健康情報を登録するAPI
    - URL：/api/healthinfo/
    - Method：POST

## ログ
- 文字コード：UTF-8

## 必要
- Python3系

## package install
pip install -r requirements.txt

## DynamoDBテーブル

| 環境 | APP_ENV | テーブル名 | 作成方法 |
| :--- | :--- | :--- | :--- |
| local | `local`（デフォルト） | `health_info_local` | AWS上に事前作成 |
| dev | `dev` | `health_info_dev` | Terraform |
| prd | `prd` | `health_info_prd` | Terraform |

dev/prdでは、環境変数 `HEALTH_INFO_DYNAMODB_TABLE_NAME` に対応するテーブル名を設定する。

ローカルDocker環境は、ホストの `~/.aws` を読み込み、AWS上の
`ap-northeast-1` にある `health_info_local` を参照する。

`health_info_local` が未作成の場合は、使用するAWSアカウントへ事前に作成する。

```bash
aws dynamodb create-table \
  --region ap-northeast-1 \
  --table-name health_info_local \
  --billing-mode PAY_PER_REQUEST \
  --attribute-definitions \
    AttributeName=seq_user_id,AttributeType=N \
    AttributeName=created_at_epoch,AttributeType=N \
  --key-schema \
    AttributeName=seq_user_id,KeyType=HASH \
    AttributeName=created_at_epoch,KeyType=RANGE
```
