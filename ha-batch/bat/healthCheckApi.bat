@echo off

rem ----------------------------------------------------------------------------------------
rem ���N�Ǘ�API_�w���X�`�F�b�NAPI�o�b�`
rem ���N�Ǘ�API�̋N�����m�F����
rem ���ۂ̊m�F��Java����HTTP�ŒʐM���s��
rem ----------------------------------------------------------------------------------------

echo ------------------------------------------------------------------------
echo START healthCheckApi.bat
echo ------------------------------------------------------------------------

rem ���ʃV�F���̓Ǎ�
call common.bat

cd %BASE_DIR%\ha-batch\target
java -jar -Dspring.profiles.active=%ENV% -Dspring.batch.job.names=heathCheckApiBatchJob %JAR_FILE%

echo ------------------------------------------------------------------------
echo END healthCheckApi.bat
echo ------------------------------------------------------------------------

cd %~dp0
