# ha-api

## 概要
健康管理アプリのAPIプロジェクト

## API一覧
- 健康情報登録API：
    - 概要：ユーザの健康情報を登録するAPI
    - URL：/api/{seqUserId}/healthinfo
    - Method：Post

- 健康情報照会API：
    - 概要：ユーザの健康情報を参照するAPI
    - URL：/api/{seqUserId}/healthinfo/{seqHealthInfoId}
    - Method：Get

- ヘルスチェックAPI：
    - 概要：APIサーバ起動しているかをチェックするAPI
    - URL：/api/healthcheck
    - Method：Get

## ログ
- 文字コード：UTF-8
- 配置先：
    - local環境：C:/app/logs/
    - EC2環境：/var/log/app/
    - ファイル名：api-${log_level}.log
