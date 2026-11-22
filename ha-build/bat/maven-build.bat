@echo off

rem ------------------------------------------------------------------------
rem maven build
rem arg1:profile (ex. local or dev)
rem ------------------------------------------------------------------------

cls

call "%~dp0maven-build-base.bat" "%~1"
if errorlevel 1 exit /b %errorlevel%

rem build dashboard
cd /d "%BASE_DIR%\ha-dashboard"
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

rem build api
cd /d "%BASE_DIR%\ha-api"
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

rem build root
cd /d "%BASE_DIR%\ha-root"
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

rem build batch
cd /d "%BASE_DIR%\ha-batch"
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

cd /d "%~dp0"
