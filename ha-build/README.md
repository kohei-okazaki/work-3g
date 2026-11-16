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

ローカル環境のMySQLは、RDSの `db_engine_version` と同じ `mysql:8.4.8` LTSを使用します。9.7系で使用したデータボリュームに、そのまま8.4.8を起動しないでください。
