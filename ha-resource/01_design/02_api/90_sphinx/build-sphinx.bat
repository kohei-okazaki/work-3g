@echo off

rem Sphinxで健康管理APIのドキュメントを作成する

cls

rem call C:\Users\okazaki\anaconda3\Scripts\activate.bat
set USER_NAME=%1

call C:\Users\%USER_NAME%\anaconda3\Scripts\activate.bat

echo ----[create rst-file from python-file]----
sphinx-apidoc -f -o ./docs .

echo ----[create html-file from rst-file]----
sphinx-build -b singlehtml ./docs ./docs/_build

cd %~dp0