@echo off

rem ------------------------------------------------------------------------
rem
rem 健康管理ドキュメントのローカルサーバの起動
rem
rem ------------------------------------------------------------------------

cls

echo start docs generate

rem 健康管理ドキュメントプロジェクトまで移動
cd ../../ha-docs

rem npmよりサーバ起動
npm run dev

cd %~dp0
