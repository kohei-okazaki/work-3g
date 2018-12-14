@echo off

rem ------------------------------------------------------------------------
rem mavenクリーンを行うbat
rem ------------------------------------------------------------------------

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



rem db/targetディレクトリをクリーン
echo ------------------------------------------------------------------------
echo START db project clean
echo ------------------------------------------------------------------------
cd %baseDir%\ha-db\target
call mvn clean package
call mvn clean install
echo ------------------------------------------------------------------------
echo END db project clean
echo ------------------------------------------------------------------------



cd %~dp0
