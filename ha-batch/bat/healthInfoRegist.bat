@echo off

rem 健康情報登録バッチ

rem 共通シェルの読込
call common.bat

cd C:\app\git\work-3g\ha-batch\target\classes

rem システム環境変数のJava(JAVA_HOME)だとeclipseでコンパイルしたときのJavaのversionが異なるので、
rem 直接、eclipseのJavaで実行する
%JAVA% -cp .;%CLASSPATH% jp.co.ha.batch.invoke.BatchEntry HealthInfoRegistBatch

cd %~dp0
