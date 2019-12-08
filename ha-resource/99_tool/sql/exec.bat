@echo off

rem PCにインストールされているMySQLへSQLを流すbat.
rem 同階層に配置しているexec.sqlを実行する
rem 実行結果は標準出力する
rem ユーザ        : root
rem パスワード    : admin
rem データベース  : work3g
rem ARG1          : ログ出力先
rem                            "console"を指定した場合、ターミナル上にSQLと処理結果が出力される
rem                            未指定の場合、本batと同階層に"result.log"としてSQLと処理結果が出力される

SET USER=root
SET DATABASE=work3g
SET PASSWORD=admin

IF %1 EQU console (
  mysql -u %USER% -D %DATABASE% -p%PASSWORD% -v < exec.sql 2>> error.log
) ELSE (
  mysql -u %USER% -D %DATABASE% -p%PASSWORD% -v < exec.sql >> result.log 2>> error.log
)

