@echo off

rem ----------------------------------------------------------------------------------------
rem 月次健康情報集計バッチ
rem 引数1:処理対象年月(YYYYMM)
rem 例：monthlyHealthInfoSummary.bat 202306
rem ----------------------------------------------------------------------------------------

echo ------------------------------------------------------------------------
echo START %~n0%~x0
echo ------------------------------------------------------------------------

rem 共通シェルの読込
call common.bat

cd %BASE_DIR%\ha-batch\target

rem 処理対象年月YYYYMM
set DATE_OPTION_VALUE=%1

java -jar -Dspring.profiles.active=%ENV% -Dspring.batch.job.name=monthlyHealthInfoSummaryBatchJob %JAR_FILE% m=%DATE_OPTION_VALUE%

echo ------------------------------------------------------------------------
echo END %~n0%~x0
echo ------------------------------------------------------------------------

cd %~dp0
