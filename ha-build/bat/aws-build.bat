@echo off

rem ------------------------------------------------------------------------
rem AWS環境のダッシュボードサーバとAPIサーバにアップロードするwarのビルドを行う
rem 詳細はaws-api-build.batとaws-dashboard-build.bat を参照
rem
rem ------------------------------------------------------------------------

rem api build
echo api build
call aws-api-build.bat

rem dashboard build
echo dashboard build
call aws-dashboard-build.bat

rem batディレクトリへ戻る
cd %~dp0
