@echo off

rem ------------------------------------------------------------------------
rem S3一覧確認バッチ
rem ------------------------------------------------------------------------

cls

aws s3api list-buckets --query Buckets