@echo off

rem ------------------------------------------------------------------------
rem EC2環境のアプリケーションの状態を確認するバッチ
rem 第一引数:環境名
rem ------------------------------------------------------------------------

cls

aws elasticbeanstalk describe-environments --environment-names %1

