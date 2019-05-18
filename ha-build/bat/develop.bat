@echo off

rem ------------------------------------------------------------------------
rem 開発環境整備用のbat
rem ------------------------------------------------------------------------

cls

echo ------------------------------------------------------------------------
echo START develop.bat
echo ------------------------------------------------------------------------

rem iniファイルを読み込む
call initialize.bat


rem ------
rem - db -
rem ------
rem ha-db\src\main\webapp\WEB-INFを用意

set directory=%baseDir%\ha-db\src\main\webapp
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %baseDir%\ha-db\src\main
  mkdir webapp
)

set directory=%baseDir%\ha-db\src\main\webapp\WEB-INF
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %baseDir%\ha-db\src\main\webapp
  mkdir WEB-INF
)


rem -------
rem - web -
rem -------
rem ha-web\src\main\webapp\WEB-INFを用意

set directory=%baseDir%\ha-web\src\main\webapp
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %baseDir%\ha-web\src\main
  mkdir webapp
)

set directory=%baseDir%\ha-web\src\main\webapp\WEB-INF
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %baseDir%\ha-web\src\main\webapp
  mkdir WEB-INF
)


rem ------------
rem - business -
rem ------------
rem ha-business\src\main\webapp\WEB-INFを用意

set directory=%baseDir%\ha-business\src\main\webapp
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %baseDir%\ha-business\src\main
  mkdir webapp
)

set directory=%baseDir%\ha-business\src\main\webapp\WEB-INF
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %baseDir%\ha-business\src\main\webapp
  mkdir WEB-INF
)


echo ------------------------------------------------------------------------
echo END develop.bat
echo ------------------------------------------------------------------------

cd %~dp0
