@echo off

rem ------------------------------------------------------------------------
rem EC2環境のダッシュボードサーバとAPIサーバにアップロードするwarのビルドを行う
rem 詳細はaws-api-build.batとaws-dashboard-build.bat を参照
rem ※※※※※maven-build.batでビルドを行うので、本ファイルは基本使用しない※※※※※
rem ------------------------------------------------------------------------

rem api build
echo api build
call aws-api-build.bat

rem dashboard build
echo dashboard build
call aws-dashboard-build.bat

rem batディレクトリへ戻る
cd %~dp0
