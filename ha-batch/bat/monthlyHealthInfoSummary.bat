@echo off

rem ----------------------------------------------------------------------------------------
rem 月次健康情報集計バッチ
rem 引数1:処理対象年月(YYYYMM)
rem ----------------------------------------------------------------------------------------

rem 共通シェルの読込
call common.bat

cd %BASE_DIR%\ha-batch\target\classes

rem -m
set DATE_OPTION=%1
rem 処理対象年月YYYYMM
set TARGET_DATE=%2

rem システム環境変数のJava(JAVA_HOME)だとeclipseでコンパイルしたときのJavaのversionと異なるので、直接、eclipseのJavaで実行する
%JAVA% -cp %CLASSPATH% %MAIN% MonthlyHealthInfoSummaryBatch %DATE_OPTION% %TARGET_DATE%

cd %~dp0
