@echo off

rem ----------------------------------------------------------------------------------------
rem �������N���W�v�o�b�`
rem ����1:�����Ώ۔N��(YYYYMM)
rem ----------------------------------------------------------------------------------------

echo ------------------------------------------------------------------------
echo START %~n0%~x0
echo ------------------------------------------------------------------------

rem ���ʃV�F���̓Ǎ�
call common.bat

cd %BASE_DIR%\ha-batch\target

rem �����Ώ۔N��YYYYMM
set DATE_OPTION_VALUE=%1

java -jar -Dspring.profiles.active=%ENV% -Dspring.batch.job.name=monthlyHealthInfoSummaryBatchJob %JAR_FILE% m=%DATE_OPTION_VALUE%

echo ------------------------------------------------------------------------
echo END %~n0%~x0
echo ------------------------------------------------------------------------

cd %~dp0
