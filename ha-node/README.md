# ha-node

## 概要
健康管理アプリで健康情報の計算処理を行うNode.jsのプロジェクト  
AWS Gatewayとlambdaで構成  

## サーバサイド言語
Node.js 14.16.0→22.x

## API一覧

- トークン発行API：
    - 概要：Node健康情報計算APIのJWTを発行するAPI
    - エンドポイント：/token
    - Method：POST

- 基礎健康情報計算API：
    - 概要：基礎健康情報を計算するAPI
    - エンドポイント：/basic
    - Method：GET

- 肺活量計算API：
    - 概要：肺活量計算を計算するAPI
    - エンドポイント：/breathing\_capacity
    - Method：GET

- カロリー計算API：
    - 概要：カロリーを計算するAPI
    - エンドポイント：/calorie
    - Method：GET

- ヘルスチェックAPI：
    - 概要：Node健康情報計算APIサーバの起動状態を確認するAPI
    - エンドポイント：/healthcheck
    - Method：GET
