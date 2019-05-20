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

set directory=%BASE_DIR%\ha-db\src\main\webapp
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %BASE_DIR%\ha-db\src\main
  mkdir webapp
)

set directory=%BASE_DIR%\ha-db\src\main\webapp\WEB-INF
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %BASE_DIR%\ha-db\src\main\webapp
  mkdir WEB-INF
)


rem -------
rem - web -
rem -------
rem ha-web\src\main\webapp\WEB-INFを用意

set directory=%BASE_DIR%\ha-web\src\main\webapp
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %BASE_DIR%\ha-web\src\main
  mkdir webapp
)

set directory=%BASE_DIR%\ha-web\src\main\webapp\WEB-INF
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %BASE_DIR%\ha-web\src\main\webapp
  mkdir WEB-INF
)


rem ------------
rem - business -
rem ------------
rem ha-business\src\main\webapp\WEB-INFを用意

set directory=%BASE_DIR%\ha-business\src\main\webapp
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %BASE_DIR%\ha-business\src\main
  mkdir webapp
)

set directory=%BASE_DIR%\ha-business\src\main\webapp\WEB-INF
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd %BASE_DIR%\ha-business\src\main\webapp
  mkdir WEB-INF
)

rem 以下の機能で必要なディレクトリを作成する
rem 1.健康情報登録バッチ
set directory="C:\app\data"
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd "C:\app"
  mkdir "data"
)
set directory="C:\app\data\healthInfoReference"
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd "C:\app\data"
  mkdir "healthInfoReference"
)

rem 2.健康情報照会画面で出力後のCSVファイル
set directory="C:\app\data\healthInfoRegist"
if not exist %directory% (
  rem ディレクトリが存在しない場合
  cd "C:\app\data"
  mkdir "healthInfoRegist"
)

echo ------------------------------------------------------------------------
echo END develop.bat
echo ------------------------------------------------------------------------

cd %~dp0
