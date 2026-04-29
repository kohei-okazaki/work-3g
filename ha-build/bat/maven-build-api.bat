@echo off

rem ------------------------------------------------------------------------
rem maven build
rem arg1:profile (ex. local or dev)
rem ------------------------------------------------------------------------

rem common -> db-> business‚Ì‡‚Ébuild
call maven-build-base.bat %1

rem build api
cd %BASE_DIR%\ha-api
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

cd %~dp0
