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

IF %1 EQU console (
  mysql -u root -D work3g -padmin -v < exec.sql 2>> error.log
) ELSE (
  mysql -u root -D work3g -padmin -v < exec.sql >> result.log 2>> error.log
)

