# ha-node

## 概要
健康管理アプリで健康情報の計算処理を行うJavaScriptのプロジェクト

## サーバサイド言語
Node.js 14.16.0

## API一覧

- トークン発行API：
    - 概要：Node健康情報計算APIのJWTを発行するAPI
    - エンドポイント：/token
    - Method：Post

- 基礎健康情報計算API：
    - 概要：基礎健康情報を計算するAPI
    - エンドポイント：/basic
    - Method：Get

- 肺活量計算API：
    - 概要：肺活量計算を計算するAPI
    - エンドポイント：/breathing\_capacity
    - Method：Get

- カロリー計算API：
    - 概要：カロリーを計算するAPI
    - エンドポイント：/calorie
    - Method：Get

- ヘルスチェックAPI：
    - 概要：Node健康情報計算APIサーバの起動状態を確認するAPI
    - エンドポイント：/healthcheck
    - Method：Get
