# ha-build

## 概要
健康管理アプリのビルド関連処理を管理するプロジェクト

## bat
概要：Windowsユーザ用に開発に必要なmavenビルドバッチやflywayを行うバッチを管理。build.iniのBASE_DIRをローカルの本プロジェクトのルートパスを合わせるようにする。
- ローカルリポジトリの場所が「C:\app\git\work-3g」の場合、修正不要
- ローカルリポジトリの場所が「C:\hoge」の場合、BASE_DIR=C:\hogeとする

## shell
概要：Macユーザ用に開発に必要なmavenビルドシェルやflywayを行うシェルを管理。また、EC2インスタンスのAMIがLinuxのため、EC2インスタンスのサーバセットアップ用にシェルを用意。

リポジトリ内のシェルスクリプトは、Maven Wrapper（`mvnw`）を含めて `#!/bin/bash` に統一します。実行環境にはBashが必要です。実行には `bash ha-build/docker/docker.sh check` のように `bash` を明示し、`sh script.sh` は使用しないでください。全25ファイルの一覧、変更内容、実行環境への影響は [シェルスクリプトの実行方針・影響一覧](SHELL_SCRIPTS.md) を参照してください。

## Docker / MySQL

ローカル環境のMySQLは、RDSの `db_engine_version` と同じ `mysql:8.4.8` LTSを使用します。既存環境の確認、バックアップ、9.7系からの切り替えと復旧は [MySQL 8.4.8への切り替え手順](docker/MYSQL_UPGRADE.md) を参照してください。9.7系で使用したデータボリュームに、そのまま8.4.8を起動しないでください。
