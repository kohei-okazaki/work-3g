@echo off

rem ------------------------------------------------------------------------
rem maven flyway
rem ------------------------------------------------------------------------

cls

rem read build.ini
call initialize.bat

rem build db
cd %BASE_DIR%\ha-db
call mvn flyway:info
call mvn flyway:migrate

cd %~dp0
