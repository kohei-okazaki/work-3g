@echo off

rem ------------------------------------------------------------------------
rem
rem root api server start
rem
rem ------------------------------------------------------------------------

cls

echo start root api server

rem build
call maven-build.bat local

rem root api server start
cd ../../ha-root
rem TODO 「ファイル名、拡張子が長すぎる」で怒られる
mvn spring-boot:run -Dmaven.test.skip=true -Plocal

echo end root api server
