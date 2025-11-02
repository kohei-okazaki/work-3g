# ha-docs

## Build Setup

```bash
# install dependencies
$ npm install

# serve with hot reload at localhost:3000
$ npm run dev

# build for production and launch server
$ npm run build
$ npm run start

# generate static project
$ npm run generate

# module update
$ vue ui

# モジュール確認コマンド
$ New-Item -ItemType Directory -Force "$env:APPDATA\npm" | Out-Null
$ npm config set prefix "$env:APPDATA\npm"
$ npm cache verify
# 一覧確認
npx --yes npm-check-updates
# 2系の安全アップデートに限定
npx --yes npm-check-updates -u --target minor
# インストール
npm install
npm audit fix


# モジュールのクリーンアップ~起動まで
$ cd C:\app\git\work-3g\ha-docs
$ if (Test-Path node_modules) { Remove-Item node_modules -Recurse -Force }
$ if (Test-Path .nuxt)        { Remove-Item .nuxt        -Recurse -Force }
$ if (Test-Path .output)      { Remove-Item .output      -Recurse -Force }
$ if (Test-Path .vite)        { Remove-Item .vite        -Recurse -Force }
$ if (Test-Path package-lock.json) { Remove-Item package-lock.json -Force }
$ npm cache verify
$ npm i
$ npx nuxt prepare
$ npm run dev


```
