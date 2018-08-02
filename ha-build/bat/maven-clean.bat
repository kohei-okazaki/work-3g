@echo off

rem iniファイルを読み込む
call initialize.bat

rem targetディレクトリをクリーン
cd %baseDir%\ha-common\target
call mvn clean

cd %baseDir%\ha-business\target
call mvn clean

cd %~dp0
