# ha-node

## 概要
健康管理アプリで健康情報の計算処理を行うJavaScriptのプロジェクト

## サーバサイド言語
Node.js 10.16.0

## API一覧

- トークン発行API：
    - 概要：Node健康情報計算APIのJWTを発行するAPI
    - URL：${domain}/token
    - Method：Post

- 基礎健康情報計算API：
    - 概要：基礎健康情報を計算するAPI
    - URL：${domain}/basic
    - Method：Post

- 肺活量計算API：
    - 概要：肺活量計算を計算するAPI
    - URL：${domain}/breathing_capacity
    - Method：Post

- カロリー計算API：
    - 概要：カロリーを計算するAPI
    - URL：${domain}/calorie
    - Method：Post
