@echo off

rem ------------------------------------------------------------------------
rem
rem 健康管理ドキュメントの生成
rem
rem ------------------------------------------------------------------------

cls

echo start docs generate

rem 健康管理ドキュメントのディレクトリまで移動
cd ../../ha-asset/04_docs

rem npmよりドキュメントを生成
npm run generate