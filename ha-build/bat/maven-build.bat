@echo off

rem iniファイルを読み込む
call initialize.bat

rem jar
set ver=1.0

cd %baseDir%\ha-common
call mvn install:install-file -Dfile=target\ha-common-%ver%.jar -DgroupId=jp.co.ha.common -DartifactId=ha-common -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true

cd %baseDir%\ha-business
call mvn install:install-file -Dfile=target\ha-business-%ver%.jar -DgroupId=jp.co.ha.business -DartifactId=ha-business -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true


cd %~dp0
