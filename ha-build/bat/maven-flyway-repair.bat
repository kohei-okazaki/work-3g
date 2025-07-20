@echo off

rem ------------------------------------------------------------------------
rem maven flyway repair
rem ------------------------------------------------------------------------

cls

rem read build.ini
call initialize.bat

rem build db
cd %BASE_DIR%\ha-db
echo mvn flyway:repair
call mvn flyway:repair

cd %~dp0
