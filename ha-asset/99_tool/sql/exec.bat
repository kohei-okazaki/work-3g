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

cls

rem ユーザ
SET USER=root
rem パスワード
SET PASSWORD=admin
rem 接続先データベース
SET DATABASE=work3g
rem 日付
SET S_DATE=%date:~0,4%%date:~5,2%%date:~8,2%
rem 時間(スペースを0に変換する)
SET time2=%time: =0%
SET S_TIME=%time2:~0,2%%time2:~3,2%%time2:~6,2%
rem ファイル名
SET FILE_NAME=%S_DATE%%S_TIME%.log

IF "%1" == console (
  echo console
  mysql -u %USER% -D %DATABASE% -p%PASSWORD% -v < exec.sql 2>> error.log
) ELSE (
  echo file
  mysql -u %USER% -D %DATABASE% -p%PASSWORD% -v < exec.sql >> %FILE_NAME% 2>> error.log
)
