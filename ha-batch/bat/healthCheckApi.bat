@echo off

rem ----------------------------------------------------------------------------------------
rem ���N�Ǘ�API_�w���X�`�F�b�NAPI�o�b�`
rem ���N�Ǘ�API�̋N�����m�F����
rem ���ۂ̊m�F��Java����HTTP�ŒʐM���s��
rem ----------------------------------------------------------------------------------------

rem ���ʃV�F���̓Ǎ�
call common.bat

cd %BASE_DIR%\ha-batch\target\classes

rem �V�X�e�����ϐ���Java(JAVA_HOME)����eclipse�ŃR���p�C�������Ƃ���Java��version�ƈقȂ�̂ŁA���ځAeclipse��Java�Ŏ��s����
%JAVA% -cp %CLASSPATH% %MAIN% HealthCheckApiBatch

cd %~dp0
