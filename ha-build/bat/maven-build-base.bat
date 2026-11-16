@echo off

rem ------------------------------------------------------------------------
rem maven build
rem arg1:profile (ex. local or dev)
rem ------------------------------------------------------------------------

cls

rem profile
set "profile=%~1"
echo profile=%profile%
if not defined profile (
  >&2 echo profile is required. Usage: %~nx0 profile
  exit /b 1
)

rem initialize.bat reads build.ini from the current directory.
pushd "%~dp0"
if errorlevel 1 exit /b %errorlevel%

if not exist "build.ini" (
  >&2 echo build.ini was not found.
  goto :build_failed
)

rem Keep BASE_DIR available to the calling build scripts.
set "BASE_DIR="
call "%~dp0initialize.bat"
if errorlevel 1 goto :build_failed
if not defined BASE_DIR (
  >&2 echo BASE_DIR is not configured in build.ini.
  goto :build_failed
)
if not exist "%BASE_DIR%\ha-pom\pom.xml" (
  >&2 echo The parent POM was not found: "%BASE_DIR%\ha-pom\pom.xml"
  goto :build_failed
)

rem Build and install the parent POM, ha-common, ha-db and ha-business.
rem Maven uses the artifact names, versions and dependencies from their POMs.
echo mvn -f "%BASE_DIR%\ha-pom\pom.xml" -pl :ha-business -am clean install -Dmaven.test.skip=true "-P%profile%"
call mvn -f "%BASE_DIR%\ha-pom\pom.xml" -pl :ha-business -am clean install -Dmaven.test.skip=true "-P%profile%"
set "build_base_exit_code=%errorlevel%"
popd
exit /b %build_base_exit_code%

:build_failed
popd
exit /b 1
