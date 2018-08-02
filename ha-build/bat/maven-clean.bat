@echo off

rem targetディレクトリをクリーン
cd %baseDir%\ha-common\target
call mvn clean

cd %baseDir%\ha-business\target
call mvn clean

cd %~dp0
