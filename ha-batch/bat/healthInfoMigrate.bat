@echo off

rem ----------------------------------------------------------------------------------------
rem ���N���A�g�o�b�`
rem ����1:�����Ώۓ�(YYYYMMDD)
rem ��FhealthInfoMigrate.bat 20230621
rem ----------------------------------------------------------------------------------------

echo ------------------------------------------------------------------------
echo START %~n0%~x0
echo ------------------------------------------------------------------------

rem ���ʃV�F���̓Ǎ�
call common.bat

cd %BASE_DIR%\ha-batch\target

rem �����Ώ۔N��YYYYMMDD
set DATE_OPTION_VALUE=%1

java -jar -Dspring.profiles.active=%ENV% -Dspring.batch.job.name=healthInfoMigrateBatchJob %JAR_FILE% d=%DATE_OPTION_VALUE%

echo ------------------------------------------------------------------------
echo END %~n0%~x0
echo ------------------------------------------------------------------------

cd %~dp0
