@echo off

rem ------------------------------------------------------------------------
rem
rem 健康管理ドキュメントのローカルサーバの起動
rem
rem ------------------------------------------------------------------------

cls

echo start docs generate

rem 健康管理ドキュメントのディレクトリまで移動
cd ../../ha-asset/04_docs

rem npmよりサーバ起動
npm run dev
