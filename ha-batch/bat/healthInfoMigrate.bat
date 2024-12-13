@echo off

rem ----------------------------------------------------------------------------------------
rem 健康情報連携バッチ
rem 引数1:処理対象日(YYYYMMDD)
rem 例：healthInfoMigrate.bat 20230621
rem ----------------------------------------------------------------------------------------

echo ------------------------------------------------------------------------
echo START %~n0%~x0
echo ------------------------------------------------------------------------

rem 共通シェルの読込
call common.bat

cd %BASE_DIR%\ha-batch\target

rem 処理対象年月YYYYMMDD
set DATE_OPTION_VALUE=%1

java -jar -Dspring.profiles.active=%ENV% -Dspring.batch.job.name=healthInfoMigrateBatchJob %JAR_FILE% d=%DATE_OPTION_VALUE%

echo ------------------------------------------------------------------------
echo END %~n0%~x0
echo ------------------------------------------------------------------------

cd %~dp0
