@echo off

rem ------------------------------------------------------------------------
rem maven build
rem arg1:profile (ex. local or ec2)
rem ------------------------------------------------------------------------

cls

rem read build.ini
call initialize.bat

rem jar.version
set ver=1.0.0
echo jar.version=%ver%

rem profile
set profile=%1
echo profile=%profile%
if "%profile%" equ "" (
  echo profile is invalid
  exit /B 0
)

rem build common
cd %BASE_DIR%\ha-common
call mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn install:install-file -Dfile=target\common-%ver%.jar -DgroupId=jp.co.ha -DartifactId=common -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem build db
cd %BASE_DIR%\ha-db
call mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn install:install-file -Dfile=target\db-%ver%.jar -DgroupId=jp.co.ha -DartifactId=db -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem build web
cd %BASE_DIR%\ha-web
call mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn install:install-file -Dfile=target\web-%ver%.jar -DgroupId=jp.co.ha -DartifactId=web -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem build business
cd %BASE_DIR%\ha-business
call mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn install:install-file -Dfile=target\business-%ver%.jar -DgroupId=jp.co.ha -DartifactId=business -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem build batch
cd %BASE_DIR%\ha-batch
call mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn install:install-file -Dfile=target\batch-%ver%.jar -DgroupId=jp.co.ha -DartifactId=batch -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem build api
cd %BASE_DIR%\ha-api
call mvn clean package -Dmaven.test.skip=true -P%profile%


rem build dashboard
cd %BASE_DIR%\ha-dashboard
call mvn clean package -Dmaven.test.skip=true -P%profile%

rem call deploy.bat

cd %~dp0
