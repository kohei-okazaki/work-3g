# ha-db

## 概要
健康管理アプリのDBアクセスやEntityを管理するプロジェクト

## テーブル一覧
詳細はha-asset内のDB.xlsxを参照

- API_LOG
- BMI_RANGE_MT
- HEALTH_INFO
- HEALTH_INFO_FILE_SETTING
- INQUIRY_MANAGEMENT
- INQUIRY_STATUS_MT
- INQUIRY_TYPE_MT
- NEWS_INFO
- ROOT_LOGIN_INFO
- ROOT_ROLE_MT
- ROOT_USER_NOTE_INFO
- ROOT_USER_ROLE_DETAIL_MT
- ROOT_USER_ROLE_MNG_MT
- USER
- USER_HEALTH_GOAL
- USER_RECOVERY_TOKEN

## DB
- RDB
    - MySQL

- Version
    - 8.4.0

## 依存関係
以下のJavaプロジェクトからの呼び出しを許容
- ha-api
- ha-batch
- ha-business
- ha-dashboard
- ha-tool
- ha-root

## マイグレーション
- 概要
    - 開発で変更になったDB定義について手でSQLを叩き、DDLを反映させずDocker上で　docker compose コマンドで反映。

- ツール
    - Flyway

- Version
    - 11.18.0
