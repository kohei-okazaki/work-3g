@echo off

rem ----------------------------------------------------------------------------------------
rem 健康管理API_ヘルスチェックAPIバッチ
rem 健康管理APIの起動を確認する
rem 実際の確認はJavaからHTTPで通信を行う
rem ----------------------------------------------------------------------------------------

rem 共通シェルの読込
call common.bat

cd %BASE_DIR%\ha-batch\target\classes

rem システム環境変数のJava(JAVA_HOME)だとeclipseでコンパイルしたときのJavaのversionと異なるので、直接、eclipseのJavaで実行する
%JAVA% -cp %CLASSPATH% %MAIN% HealthCheckApiBatch

cd %~dp0
