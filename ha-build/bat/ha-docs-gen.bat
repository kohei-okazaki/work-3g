@echo off

rem ------------------------------------------------------------------------
rem
rem 健康管理ドキュメントの生成
rem
rem ------------------------------------------------------------------------

cls

echo ------------------------------------------------------------------------
echo start docs generate
echo ------------------------------------------------------------------------

rem 健康管理ドキュメントプロジェクトまで移動
cd ../../ha-docs

rem npmよりドキュメントを生成
npm run generate

echo ------------------------------------------------------------------------
echo end docs generate
echo ------------------------------------------------------------------------

cd %~dp0