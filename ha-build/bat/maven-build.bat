@echo off

rem iniファイルを読み込む
call initialize.bat



rem jar.version
set ver=1.0
echo jar.version = %ver%

rem commonのjarを作成
cd %baseDir%\ha-common
call mvn package
call mvn install:install-file -Dfile=target\ha-common-%ver%.jar -DgroupId=jp.co.ha.common -DartifactId=ha-common -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true


rem businessのjarを作成
cd %baseDir%\ha-business
call mvn package
call mvn install:install-file -Dfile=target\ha-business-%ver%.jar -DgroupId=jp.co.ha.business -DartifactId=ha-business -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true


cd %~dp0
echo ------------------------------------------------------------------------
echo START deploy.bat
echo ------------------------------------------------------------------------
call deploy.bat
echo ------------------------------------------------------------------------
echo END deploy.bat
echo ------------------------------------------------------------------------


cd %~dp0
