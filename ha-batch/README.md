# ha-batch

## 概要
健康管理アプリのバッチプロジェクト

## バッチ一覧
- ヘルスチェックAPIバッチ：healthCheckApi.bat/healthCheckApi.sh
    - 概要：APIサーバ起動しているかをチェックするAPIを呼び出すバッチ
- 健康情報ファイル登録バッチ：healthInfoFileRegist.bat/healthInfoFileRegist.sh
    - 概要：指定フォーマットのJSONの情報をもとに健康情報管理API_健康情報登録APIを呼び出すバッチ
- 月次健康情報集計バッチ：monthlyHealthInfoSummary.bat/monthlyHealthInfoSummary.sh
    - 概要：特定の月の健康情報を検索し、CSVに変換し、S3へのアップロードを行うバッチ

## ログ
- 文字コード：MS932
- 配置先：
    - local環境：C:/app/logs/
    - EC2環境：/var/log/app/
    - ファイル名：batch-${log_level}.log
