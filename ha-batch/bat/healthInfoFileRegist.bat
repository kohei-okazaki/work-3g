@echo off

rem ----------------------------------------------------------------------------------------
rem ���N���t�@�C���o�^�o�b�`
rem �w�肵���f�B���N�g����JSON�t�@�C�����猒�N����o�^����
rem ----------------------------------------------------------------------------------------

rem ���ʃV�F���̓Ǎ�
call common.bat

cd %BASE_DIR%\ha-batch\target\classes

rem �V�X�e�����ϐ���Java(JAVA_HOME)����eclipse�ŃR���p�C�������Ƃ���Java��version�ƈقȂ�̂ŁA���ځAeclipse��Java�Ŏ��s����
%JAVA% -cp %CLASSPATH% %MAIN% HealthInfoFileRegistBatch

cd %~dp0
