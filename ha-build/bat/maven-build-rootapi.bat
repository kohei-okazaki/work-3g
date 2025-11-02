@echo off

rem ------------------------------------------------------------------------
rem maven build
rem arg1:profile (ex. local or dev1)
rem ------------------------------------------------------------------------

rem common -> db-> business‚Ì‡‚Ébuild
call maven-build-base.bat %1

rem build root
cd %BASE_DIR%\ha-root
echo mvn clean package -Dmaven.test.skip=true -P%1
call mvn clean package -Dmaven.test.skip=true -P%1

cd %~dp0