# ha-build

## 概要
健康管理アプリのビルド関連処理を管理するプロジェクト

## AWS関連の配置

- [aws/IaC](aws/IaC/)：アプリケーション基盤・分析基盤のTerraform定義。
- [ha-calc-api](../ha-calc-api/README.md)：計算APIのLambda実装・CloudFormation定義・ビルド・デプロイ手順。
- [ha-docs/aws/local](../ha-docs/aws/local/)：Step FunctionsとAthenaのローカル検証・手動設定用参考資料。

## bat
概要：Windowsユーザ用に開発に必要なmavenビルドバッチやflywayを行うバッチを管理。build.iniのBASE_DIRをローカルの本プロジェクトのルートパスを合わせるようにする。
- ローカルリポジトリの場所が「C:\app\git\work-3g」の場合、修正不要
- ローカルリポジトリの場所が「C:\hoge」の場合、BASE_DIR=C:\hogeとする

## Docker / MySQL

ローカル環境のMySQLは、RDSの `db_engine_version` と同じ MySQL 9.7 LTS の `9.7.4` を使用します。Docker のイメージは `mysql:9.7.4` に固定し、`latest` や `lts` の更新による意図しないバージョン変更を避けます。

既存の9.7.4用データボリュームはそのまま使用できます。8.4系のデータを使用している場合は、バックアップとアップグレード互換性の確認を先に行ってください。9.7系のデータを8.4系のサーバーで開くことはできません。

RDS MySQL 9.7 は標準でTLS接続を要求するため、Terraformがアプリケーションに渡すJDBC URLでは `sslMode=REQUIRED` を指定します。ローカルDocker内の接続設定はローカル開発用です。RDSの設定やアップグレード時の注意点は [IaC README](aws/IaC/README.md) を参照してください。

### Flywayの互換性確認

Spring Boot `4.1.2` のBOMで解決されるFlyway本体・MySQLモジュールは `12.4.0`、Connector/Jは `9.7.0` です。Flyway Mavenプラグインも `12.4.0` を使用します。

2026年11月22日、既存DBとは独立したMySQL `9.7.4` コンテナで `require_secure_transport=ON` とし、TLS接続、既存マイグレーション3本の初回適用、`validate`、再実行時の適用0件を確認しました。Mavenプラグインの `info`・`validate`・`migrate` も成功しています。RDS実環境のアップグレードと既存データの移行は別途検証が必要です。

[Flyway公式の検証済み一覧](https://documentation.red-gate.com/flyway/reference/database-driver-reference/mysql) はMySQL `9.4` までのため、`9.7` では検証済みバージョンを超える旨の警告が出ます。また、既存SQLの `/` 区切り日時にも非推奨警告が出ますが、上記検証では処理が成功しています。適用済みSQLのチェックサムを維持するため、既存マイグレーションは変更していません。
