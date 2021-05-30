@echo off

rem ----------------------------------------------------------------------------------------
rem 月次健康情報集計バッチ
rem 引数1:処理対象年月(YYYYMM)
rem ----------------------------------------------------------------------------------------

echo ------------------------------------------------------------------------
echo START monthlyHealthInfoSummary.bat
echo ------------------------------------------------------------------------

rem 共通シェルの読込
call common.bat

cd %BASE_DIR%\ha-batch\target

rem 処理対象年月YYYYMM
set DATE_OPTION_VALUE=%1

java -jar -Dspring.profiles.active=%ENV% -Dspring.batch.job.names=ｍonthlyHealthInfoSummaryBatchJob %JAR_FILE% m=%DATE_OPTION_VALUE%

echo ------------------------------------------------------------------------
echo END monthlyHealthInfoSummary.bat
echo ------------------------------------------------------------------------

cd %~dp0
