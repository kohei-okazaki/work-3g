# ha-docs

## DB定義・SQL

| 配置先 | 内容 |
| --- | --- |
| [db/DB.xlsx](./db/DB.xlsx) | テーブル定義とマスタデータの定義書。 |
| [db/ddl](./db/ddl/) | 定義書から生成するDDL。 |
| [db/dml](./db/dml/) | 定義書から生成するマスタデータ登録用DML。 |
| [db/others](./db/others/) | DB・ユーザの初期構築SQL、削除SQL、テーブル定義参照SQL。 |

SQL生成ツールは [ha-tools/db](../ha-tools/db/) で管理する。
Flywayが適用するマイグレーションSQLは [ha-db/src/main/resources/db/migration](../ha-db/src/main/resources/db/migration/) で管理し、`classpath:/db/migration`から読み込む。

## AWSローカル検証資料

| 配置先 | 内容 |
| --- | --- |
| [aws/local/step-functions](./aws/local/step-functions/) | 健康情報分析ワークフローの参考JSON。 |
| [aws/local/athena](./aws/local/athena/) | Athenaテーブルの作成・確認用の参考SQL。 |

これらはローカル検証・手動設定時の参考資料で、自動デプロイには使用しない。
現行環境のインフラ定義は [ha-build/aws/IaC](../ha-build/aws/IaC/) で管理しており、参考資料とは定義内容が異なる。
健康情報計算APIの実装・CloudFormation・デプロイ手順は [ha-calc-api](../ha-calc-api/README.md) を参照する。

## Build Setup

Node.js `^22.22.3 || ^24.15.0 || >=26.0.0` が必要です。
図の表示には ES2024 対応ブラウザ（Safari 17.4 以降など）を使用してください。

```bash
# 依存ライブラリのインストール
$ npm ci

# 図の非同期描画に関する回帰テスト
$ npm test

# ローカルサーバ起動
$ npm run dev

# ビルド
$ npm run build

# ビルド済サーバ起動
$ npm run start

# 静的ファイル生成コマンド
$ npm run generate
```
