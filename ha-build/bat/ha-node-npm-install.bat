@echo off

rem ------------------------------------------------------------------------
rem
rem NodeAPIのモジュールのインストールを行う
rem node_modulesとpackage-lock.jsonを作成
rem
rem ------------------------------------------------------------------------

cls

echo start npm install

rem ha-nodeまで移動
cd ../../ha-node

rem nodeモジュールをインストール
npm install
