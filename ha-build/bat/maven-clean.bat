@echo off

rem iniファイルを読み込む
call initialize.bat



rem common/targetディレクトリをクリーン
echo ------------------------------------------------------------------------
echo START common project clean
echo ------------------------------------------------------------------------
cd %baseDir%\ha-common\target
call mvn clean package
call mvn clean install
echo ------------------------------------------------------------------------
echo END common project clean
echo ------------------------------------------------------------------------



rem business/targetディレクトリをクリーン
echo ------------------------------------------------------------------------
echo START business project clean
echo ------------------------------------------------------------------------
cd %baseDir%\ha-business\target
call mvn clean package
call mvn clean install
echo ------------------------------------------------------------------------
echo END business project clean
echo ------------------------------------------------------------------------



cd %~dp0
