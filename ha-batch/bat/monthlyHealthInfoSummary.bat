@echo off

rem ----------------------------------------------------------------------------------------
rem �������N���W�v�o�b�`
rem ����1:�����Ώ۔N��(YYYYMM)
rem ----------------------------------------------------------------------------------------

rem ���ʃV�F���̓Ǎ�
call common.bat

cd %BASE_DIR%\ha-batch\target\classes

rem -m
set DATE_OPTION=%1
rem �����Ώ۔N��YYYYMM
set TARGET_DATE=%2

rem �V�X�e�����ϐ���Java(JAVA_HOME)����eclipse�ŃR���p�C�������Ƃ���Java��version�ƈقȂ�̂ŁA���ځAeclipse��Java�Ŏ��s����
%JAVA% -cp %CLASSPATH% %MAIN% MonthlyHealthInfoSummaryBatch %DATE_OPTION% %TARGET_DATE%

cd %~dp0
