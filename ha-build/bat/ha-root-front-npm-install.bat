@echo off

rem ------------------------------------------------------------------------
rem
rem Nodeのモジュールのインストールを行う
rem node_modulesとpackage-lock.jsonを作成
rem
rem ------------------------------------------------------------------------

cls

echo start npm install

rem ha-root/frontまで移動
cd ../../ha-root/front

rem nodeモジュールをインストール
npm install

cd %~dp0
