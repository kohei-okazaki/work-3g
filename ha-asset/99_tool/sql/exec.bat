@echo off

rem PC�ɃC���X�g�[������Ă���MySQL��SQL�𗬂�bat.
rem ���K�w�ɔz�u���Ă���exec.sql�����s����
rem ���s���ʂ͕W���o�͂���
rem ���[�U        : root
rem �p�X���[�h    : admin
rem �f�[�^�x�[�X  : work3g
rem ARG1          : ���O�o�͐�
rem                            "console"���w�肵���ꍇ�A�^�[�~�i�����SQL�Ə������ʂ��o�͂����
rem                            ���w��̏ꍇ�A�{bat�Ɠ��K�w��"result.log"�Ƃ���SQL�Ə������ʂ��o�͂����

cls

rem ���[�U
SET USER=root
rem �p�X���[�h
SET PASSWORD=admin
rem �ڑ���f�[�^�x�[�X
SET DATABASE=work3g
rem ���t
SET S_DATE=%date:~0,4%%date:~5,2%%date:~8,2%
rem ����(�X�y�[�X��0�ɕϊ�����)
SET time2=%time: =0%
SET S_TIME=%time2:~0,2%%time2:~3,2%%time2:~6,2%
rem �t�@�C����
SET FILE_NAME=%S_DATE%%S_TIME%.log

IF "%1" == console (
  echo console
  mysql -u %USER% -D %DATABASE% -p%PASSWORD% -v < exec.sql 2>> error.log
) ELSE (
  echo file
  mysql -u %USER% -D %DATABASE% -p%PASSWORD% -v < exec.sql >> %FILE_NAME% 2>> error.log
)
