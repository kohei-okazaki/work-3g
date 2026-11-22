# ha-batch

## 概要
健康管理アプリのバッチプロジェクト

## バッチ一覧
- ヘルスチェックAPIバッチ：docker/health_check.sh
    - 概要：APIサーバ起動しているかをチェックするAPIを呼び出すバッチ
- 健康情報ファイル登録バッチ：docker/health_info_file_regist.sh
    - 概要：指定フォーマットのJSONの情報をもとに健康情報管理API_健康情報登録APIを呼び出すバッチ
- 月次健康情報集計バッチ：docker/monthly_health_info_summary.sh
    - 概要：特定の月の健康情報を検索し、CSVに変換し、S3へのアップロードを行うバッチ
- 健康情報連携バッチ：docker/health_info_migrate.sh
    - 概要：指定した日の健康情報をha-trackへ連携する。
- AWS SQS取込バッチ：docker/aws_sqs_import.sh
    - 概要：SQSからAPI通信ログのメッセージを取得し、API_LOGに登録する。
- データパージバッチ：docker/data_purge.sh
    - 概要：論理削除済みで保持期間の条件に合致するデータを物理削除する。
- 日次データ分析連携JobSet：docker/daily_analysis_jobset.sh
    - 概要：指定した年月日の健康情報/ユーザ情報/API通信ログ/バッチ実行ログをCSVファイルに出力し圧縮してS3へ転送するための各子シェルでジョブを実行する。
- 日次健康情報データ分析連携バッチ：docker/daily_analysis_health_info.sh
    - 概要：指定した年月日の健康情報をCSVファイルに出力し圧縮してS3へ転送する。
- 日次ユーザ情報データ分析連携バッチ：docker/daily_analysis_user.sh
    - 概要：指定した年月日のユーザ情報をCSVファイルに出力し圧縮してS3へ転送する。
- 日次API通信ログデータ分析連携バッチ：docker/daily_analysis_api_log.sh
    - 概要：指定した年月日のAPI通信ログをCSVファイルに出力し圧縮してS3へ転送する。
- 日次バッチ実行ログデータ分析連携バッチ：docker/daily_analysis_batch_log.sh
    - 概要：指定した年月日のバッチ実行ログをCSVファイルに出力し圧縮してS3へ転送する。

## 設計書

[バッチ設計書一覧](../ha-docs/design/03_batch/README.md)を参照。

## Spring Batchの実行履歴

`spring-boot-starter-batch-jdbc`により、既存のDataSourceとTransactionManagerを使って
全ジョブの実行履歴を`BATCH_*`テーブルへ保存する。
シェルの`common.sh`で生成した`run.id`は文字列のジョブパラメータとして
`BATCH_JOB_EXECUTION_PARAMS`の`PARAMETER_NAME = 'run.id'`に保存される。
Slack通知の`id`は、そのパラメータに紐づく`JOB_EXECUTION_ID`となる。

既存環境では、新しいバッチを実行する前に
`V1.0.3__spring_batch_6_job_instance_sequence.sql`をFlywayで適用する。
ローカルのDocker Compose環境ではFlywayが有効なため、WSLでリポジトリのルートから
`bash ha-build/docker/docker.sh build`を実行すると、イメージの再ビルドとサービス起動に続いて
起動時にマイグレーションが適用される。起動ログで成功を確認してから通常のバッチ起動シェルを実行する。
`docker.sh build`自体はha-batchのジョブを実行しない。
Docker Compose以外の環境では通常のFlyway手順で適用する。
このマイグレーションは`BATCH_JOB_SEQ`をBatch 6用の`BATCH_JOB_INSTANCE_SEQ`へ変更し、
既存の採番値と実行履歴を引き継ぐ。既存の初期化SQLの再実行や採番値のリセットは行わない。
`spring.batch.jdbc.initialize-schema=never`は維持し、スキーマはFlywayで管理する。

保存結果は次のSQLで確認できる。

```sql
SELECT e.JOB_EXECUTION_ID, i.JOB_NAME, e.STATUS, p.PARAMETER_VALUE AS RUN_ID
FROM BATCH_JOB_EXECUTION e
JOIN BATCH_JOB_INSTANCE i ON i.JOB_INSTANCE_ID = e.JOB_INSTANCE_ID
JOIN BATCH_JOB_EXECUTION_PARAMS p ON p.JOB_EXECUTION_ID = e.JOB_EXECUTION_ID
WHERE p.PARAMETER_NAME = 'run.id'
ORDER BY e.JOB_EXECUTION_ID DESC;
```

通常の起動スクリプトは毎回異なる`run.id`を渡すため、同じ対象日でも新しいジョブとして実行する。
同じジョブ名・識別パラメータで完了済みの実行を再度起動した場合は、Spring Batchの重複実行判定が働く。

## ログ
- 文字コード：UTF-8
- 配置先：
    - local環境：/var/app/log
    - dev環境：/var/app/log
    - ファイル名：batch-${log_level}.log
