@echo off

rem ------------------------------------------------------------------------
rem
rem 健康情報計算APIサーバを起動するスクリプト
rem
rem ------------------------------------------------------------------------

cls

echo start npm start

rem ha-nodeまで移動
cd ../../ha-node

rem npmよりサーバ起動
npm start