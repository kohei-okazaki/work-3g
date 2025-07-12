@echo off

rem ------------------------------------------------------------------------
rem EC2のElasticBeanstalkの状態を確認するバッチ
rem 第一引数:アプリケーション名
rem ------------------------------------------------------------------------

cls

aws elasticbeanstalk describe-applications --application-name %1
