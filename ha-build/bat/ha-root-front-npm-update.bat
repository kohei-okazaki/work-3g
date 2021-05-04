@echo off

rem ------------------------------------------------------------------------
rem
rem Nodeのモジュールの更新を行う
rem package.jsonのdependencies内のversionの最新化
rem
rem ------------------------------------------------------------------------

cls

echo start npm update

rem ha-root/frontまで移動
cd ../../ha-root/front

rem nodeモジュールをアップデート
npm update

cd %~dp0
