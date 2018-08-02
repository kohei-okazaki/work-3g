@echo off

rem jar
set ver=1.0

cd %commonDir%
call mvn install:install-file -Dfile=target/ha-common-1.0.jar -DgroupId=jp.co.ha.common -DartifactId=ha-common -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true

cd %businessDir%
call mvn install:install-file -Dfile=target/ha-business-1.0.jar -DgroupId=jp.co.ha.business -DartifactId=ha-business -Dversion=%ver% -Dpackaging=jar -DgeneratePom=true


cd %~dp0
