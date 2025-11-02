@echo off

rem ------------------------------------------------------------------------
rem maven build
rem arg1:profile (ex. local or dev1)
rem ------------------------------------------------------------------------

cls

call maven-build-base.bat %1

rem build dashboard
cd %BASE_DIR%\ha-dashboard
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

rem build api
cd %BASE_DIR%\ha-api
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

rem build root
cd %BASE_DIR%\ha-root
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

rem build batch
cd %BASE_DIR%\ha-batch
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

cd %~dp0
