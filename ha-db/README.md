# ha-db

## 概要
健康管理アプリのDBアクセスやEntityを管理するプロジェクト

## テーブル一覧

詳細はha-asset内のDB.xlsxを参照

- ACCOUNT
- ACCOUNT_RECOVERY_TOKEN
- API_COMMUNICATION_DATA
- BMI_RANGE_MT
- HEALTH_INFO
- HEALTH_INFO_FILE_SETTING
- ROOT_LOGIN_INFO
- ROOT_USER_ROLE_MNG_MT
- ROOT_USER_ROLE_DETAIL_MT
- ROOT_ROLE_MT

## DB
- RDB
    - MySQL

- Version
    - 8系

## 依存関係
以下のJavaプロジェクトからの呼び出しを許容
- ha-api
- ha-batch
- ha-business
- ha-dashboard
- ha-tool
- ha-web
- ha-root

## マイグレーション
- 概要
    - 開発で変更になったDB定義について手でSQLを叩き、DDLを反映させず、「ツール」を使ってDBのバージョン管理を行う。実際はmavenコマンドで実行する

- ツール
    - Flyway

- Version
    - 7系
