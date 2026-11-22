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
- Python 3.14.8（Docker）、Python 3.14 系（CI）
- Django 5.2 LTS（2028年4月までサポート）

Python は Django 5.2 LTS が公式に対応する最新の 3.14 系を使用する。
Python、Django REST framework、boto3、Gunicorn、Ruff には LTS の区分がないため、
この構成に対応する最新の安定版を使用する（2026年10月18日確認）。

更新時の確認先：
- [Python リリース](https://www.python.org/downloads/)
- [Django のサポート期間](https://www.djangoproject.com/download/)
- [Django の Python 対応表](https://docs.djangoproject.com/en/5.2/faq/install/#what-python-version-can-i-use-with-django)
- [Django REST framework](https://pypi.org/project/djangorestframework/)
- [boto3](https://pypi.org/project/boto3/)
- [Gunicorn](https://pypi.org/project/gunicorn/)
- [Ruff](https://pypi.org/project/ruff/)

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
