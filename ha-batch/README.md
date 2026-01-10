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
    - 概要：SQSからメッセージを取得し、以下のテーブルに登録する。
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

## ログ
- 文字コード：UTF-8
- 配置先：
    - local環境：/var/app/log
    - dev1環境：/var/app/log
    - ファイル名：batch-${log_level}.log
