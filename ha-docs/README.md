# ha-docs

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
