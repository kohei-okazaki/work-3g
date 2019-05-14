@echo off

rem ----------------------------------------------------------------------------------------
rem 健康情報ファイル登録バッチ
rem 指定したディレクトリのJSONファイルから健康情報を登録する
rem ----------------------------------------------------------------------------------------

rem 共通シェルの読込
call common.bat

cd C:\app\git\work-3g\ha-batch\target\classes

rem システム環境変数のJava(JAVA_HOME)だとeclipseでコンパイルしたときのJavaのversionと異なるので、直接、eclipseのJavaで実行する
%JAVA% -cp .;%CLASSPATH% jp.co.ha.batch.invoke.BatchEntry HealthInfoFileRegistBatch

cd %~dp0
