@echo off

rem ------------------------------------------------------------------------
rem 開発環境整備用のbat
rem
rem 1. 健康情報照会画面で出力後のCSVファイルを保管するディレクトリを用意
rem 2. 健康情報登録バッチで処理対象ファイルディレクトリ
rem
rem ------------------------------------------------------------------------

cls

echo ------------------------------------------------------------------------
echo START develop.bat
echo ------------------------------------------------------------------------

rem iniファイルを読み込む
call initialize.bat

rem 基底ディレクトリ
set BASE_DIR="C:\app"

rem 以下の機能で必要なディレクトリを作成する
rem 1. 健康情報照会画面で出力後のCSVディレクトリ
set DATA_DIR=%BASE_DIR%"\data"
if not exist %DATA_DIR% (
  rem ディレクトリが存在しない場合
  cd %BASE_DIR%
  mkdir "data"
)
set REF_DIR=%DATA_DIR%"\healthInfoReference"
if not exist %REF_DIR% (
  rem ディレクトリが存在しない場合
  cd %DATA_DIR%
  mkdir "healthInfoReference"
)

rem 2. 健康情報登録バッチで処理対象ファイルディレクトリ
set BAT_REG_DIR=%DATA_DIR%"\healthInfoRegist"
if not exist %BAT_REG_DIR% (
  rem ディレクトリが存在しない場合
  cd %DATA_DIR%
  mkdir "healthInfoRegist"
)

echo ------------------------------------------------------------------------
echo END develop.bat
echo ------------------------------------------------------------------------

cd %~dp0
