@echo off

rem ----------------------------------------------------------------------------------------
rem 共通処理バッチ
rem 環境変数やクラスパスの設定などを行う
rem ----------------------------------------------------------------------------------------

rem 基底ディレクトリ
set BASE_DIR=C:\app\git\work-3g
rem 環境
set ENV=local
rem jar
set JAR_FILE=batch-1.0.0.jar

rem 文字コードをUTF-8に変更する
rem 以下を有効にするとSlackAPIが叩けなくなる
rem chcp 65001
