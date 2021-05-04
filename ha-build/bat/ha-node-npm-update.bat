@echo off

rem ------------------------------------------------------------------------
rem
rem Nodeのモジュールの更新を行う
rem package.jsonのdependencies内のversionの最新化
rem
rem ------------------------------------------------------------------------

cls

echo start npm update

rem ha-nodeまで移動
cd ../../ha-node

rem nodeモジュールをアップデート
npm update

cd %~dp0
