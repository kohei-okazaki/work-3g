@echo off

rem iniファイルを読み込む
call initialize.bat

rem targetディレクトリをクリーン
cd %baseDir%\ha-common\target
call mvn clean package
call mvn clean install

cd %baseDir%\ha-business\target
call mvn clean package
call mvn clean install

cd %~dp0
