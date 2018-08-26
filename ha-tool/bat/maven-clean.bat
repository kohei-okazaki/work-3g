@echo off

rem iniファイルを読み込む
echo START initialize.bat
call initialize.bat
echo END initialize.bat

rem common/targetディレクトリをクリーン
echo START common project clean
cd %baseDir%\ha-common\target
call mvn clean package
call mvn clean install
echo END common project clean


rem business/targetディレクトリをクリーン
echo START business project clean
cd %baseDir%\ha-business\target
call mvn clean package
call mvn clean install
echo END business project clean


cd %~dp0
