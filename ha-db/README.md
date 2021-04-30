# ha-db

## 概要
健康管理アプリのDBアクセスやEntityを管理するプロジェクト

## テーブル一覧

詳細はha-asset内のDB.xlsxを参照

- ACCOUNT
- ACCOUNT\_RECOVERY\_TOKEN
- API\_COMMUNICATION\_DATA
- BMI\_RANGE\_MT
- HEALTH\_INFO
- HEALTH\_INFO\_FILE\_SETTING
- ROOT\_LOGIN\_INFO
- ROOT\_USER\_ROLE\_MNG\_MT
- ROOT\_USER\_ROLE\_DETAIL\_MT
- ROOT\_ROLE\_MT
- ROOT\_USER\_NOTE\_INFO

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
- ha-root

## マイグレーション
- 概要
    - 開発で変更になったDB定義について手でSQLを叩き、DDLを反映させず、「ツール」を使ってDBのバージョン管理を行う。実際はmavenコマンドで実行する

- ツール
    - Flyway

- Version
    - 7系
