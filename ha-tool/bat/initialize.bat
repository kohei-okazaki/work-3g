@echo off

rem build.iniを読込
for /F "delims== tokens=1,2" %%i in (build.ini) do (
  set %%i=%%j
)

cd %~dp0
