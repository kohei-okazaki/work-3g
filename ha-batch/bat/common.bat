@echo off

rem 共通処理バッチ
rem クラスパスの設定と文字コードをUTF-8に変更
cls

set CLASSPATH=C:\batchTest\lib\*
set JAVA=C:\app\pleiades\java\11\bin\java

rem 文字コードをUTF-8に変更する
rem chcp 65001

