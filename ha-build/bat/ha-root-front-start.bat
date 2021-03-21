@echo off

rem ------------------------------------------------------------------------
rem
rem 管理者用サイト(Front)のローカルサーバを起動
rem
rem ------------------------------------------------------------------------

cls

echo start root front local

rem ha-root/frontまで移動
cd ../../ha-root/front

rem npmよりサーバ起動
npm run dev
