@echo off

rem ------------------------------------------------------------------------
rem maven build
rem arg1:profile (ex. local or dev1)
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
echo mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn clean package -Dmaven.test.skip=true -P%profile%

echo mvn install:install-file -Dfile=target\common-%ver%.jar -DgroupId=jp.co.ha -DartifactId=common -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip
call mvn install:install-file -Dfile=target\common-%ver%.jar -DgroupId=jp.co.ha -DartifactId=common -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip

rem build db
cd %BASE_DIR%\ha-db
echo mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn clean package -Dmaven.test.skip=true -P%profile%

echo mvn install:install-file -Dfile=target\db-%ver%.jar -DgroupId=jp.co.ha -DartifactId=db -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip
call mvn install:install-file -Dfile=target\db-%ver%.jar -DgroupId=jp.co.ha -DartifactId=db -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip

rem build business
cd %BASE_DIR%\ha-business
echo mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn clean package -Dmaven.test.skip=true -P%profile%

echo mvn install:install-file -Dfile=target\business-%ver%.jar -DgroupId=jp.co.ha -DartifactId=business -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip
call mvn install:install-file -Dfile=target\business-%ver%.jar -DgroupId=jp.co.ha -DartifactId=business -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip

rem build batch
cd %BASE_DIR%\ha-batch
echo mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn clean package -Dmaven.test.skip=true -P%profile%

echo mvn install:install-file -Dfile=target\batch-%ver%.jar -DgroupId=jp.co.ha -DartifactId=batch -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip
call mvn install:install-file -Dfile=target\batch-%ver%.jar -DgroupId=jp.co.ha -DartifactId=batch -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip

rem build root
cd %BASE_DIR%\ha-root
echo mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn clean package -Dmaven.test.skip=true -P%profile%

echo mvn install:install-file -Dfile=target\root-%ver%.jar -DgroupId=jp.co.ha -DartifactId=root -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip
call mvn install:install-file -Dfile=target\root-%ver%.jar -DgroupId=jp.co.ha -DartifactId=root -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip

rem build api
cd %BASE_DIR%\ha-api
echo mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn clean package -Dmaven.test.skip=true -P%profile%

rem build dashboard
cd %BASE_DIR%\ha-dashboard
echo mvn clean package -Dmaven.test.skip=true -P%profile%
call mvn clean package -Dmaven.test.skip=true -P%profile%

rem call deploy.bat

cd %~dp0
