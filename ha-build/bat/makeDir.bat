@echo off

rem 引数で指定したディレクトリ配下に指定した名前のディレクトリを作成する
rem もしディレクトリが存在する場合、そのまま終了する
rem 第1引数:親ディレクトリ
rem 第2引数:作成したいディレクトリ名

cd %1
if not exist %2\ (
	echo dir create
	mkdir %2
)

rem 呼び出し元のディレクトリまで戻る
cd %~dp0
