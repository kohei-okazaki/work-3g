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
cd %baseDir%\ha-common
call mvn clean package -DskipTests
call mvn install
call mvn install:install-file -Dfile=target\ha-common-%ver%.jar -DgroupId=jp.co.ha.common -DartifactId=ha-common -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem dbのjarを作成
cd %baseDir%\ha-db
call mvn clean package -DskipTests
call mvn install
call mvn install:install-file -Dfile=target\ha-db-%ver%.jar -DgroupId=jp.co.ha.db -DartifactId=ha-db -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem webのjarを作成
cd %baseDir%\ha-web
call mvn clean package -DskipTests
call mvn install
call mvn install:install-file -Dfile=target\ha-web-%ver%.jar -DgroupId=jp.co.ha.web -DartifactId=ha-web -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


rem businessのjarを作成
cd %baseDir%\ha-business
call mvn clean package -DskipTests
call mvn install
call mvn install:install-file -Dfile=target\ha-business-%ver%.jar -DgroupId=jp.co.ha.business -DartifactId=ha-business -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true -Dmaven.test.skip


cd %~dp0
echo ------------------------------------------------------------------------
echo START deploy.bat
echo ------------------------------------------------------------------------
call deploy.bat
echo ------------------------------------------------------------------------
echo END deploy.bat
echo ------------------------------------------------------------------------


cd %~dp0
