@echo off

rem ------------------------------------------------------------------------
rem 各batファイルで読み込む共通bat
rem ------------------------------------------------------------------------

rem build.iniを読込
echo ------------------------------------------------------------------------
echo START initialize.bat
echo ------------------------------------------------------------------------
echo read build.ini
for /F "delims== tokens=1,2" %%i in (build.ini) do (
  set %%i=%%j
)
echo ------------------------------------------------------------------------
echo END initialize.bat
echo ------------------------------------------------------------------------



cd %~dp0
