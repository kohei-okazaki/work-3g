@echo off

rem ------------------------------------------------------------------------
rem maven-buildを行うbat
rem ------------------------------------------------------------------------

cls

rem iniファイルを読み込む
call initialize.bat



rem jar.version
set ver=1.0
echo jar.version = %ver%

rem commonのjarを作成
cd %BASE_DIR%\ha-common
rem call mvn clean package -DskipTests
call mvn clean package -Dmaven.test.skip=true
rem call mvn install
rem call mvn install:install-file -Dfile=target\common-%ver%.jar -DgroupId=jp.co.ha -DartifactId=common -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem dbのjarを作成
cd %BASE_DIR%\ha-db
rem call mvn clean package -DskipTests
call mvn clean package -Dmaven.test.skip=true
rem call mvn install
rem call mvn install:install-file -Dfile=target\db-%ver%.jar -DgroupId=jp.co.ha -DartifactId=db -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem webのjarを作成
cd %BASE_DIR%\ha-web
rem call mvn clean package -DskipTests
call mvn clean package -Dmaven.test.skip=true
rem call mvn install
rem call mvn install:install-file -Dfile=target\web-%ver%.jar -DgroupId=jp.co.ha -DartifactId=web -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem businessのjarを作成
cd %BASE_DIR%\ha-business
rem call mvn clean package -DskipTests
call mvn clean package -Dmaven.test.skip=true
rem call mvn install
rem call mvn install:install-file -Dfile=target\business-%ver%.jar -DgroupId=jp.co.ha -DartifactId=business -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip

call deploy.bat

cd %~dp0
