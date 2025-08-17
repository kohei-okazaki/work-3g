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
mvn spring-boot:run -Dmaven.test.skip=true -Plocal -Dspring-boot.run.profiles=local

cd %~dp0

echo end root api server
