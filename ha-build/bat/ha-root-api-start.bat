@echo off

rem ------------------------------------------------------------------------
rem
rem root api server start
rem
rem ------------------------------------------------------------------------

cls

echo ------------------------------------------------------------------------
echo start root api server
echo ------------------------------------------------------------------------

rem build
call maven-build.bat local

rem root api server start
cd ../../ha-root
mvn spring-boot:run -Dmaven.test.skip=true -Plocal -Dspring-boot.run.profiles=local

cd %~dp0
