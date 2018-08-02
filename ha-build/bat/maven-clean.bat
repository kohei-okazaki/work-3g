@echo off

cd %commonDir%
call mvn clean

cd %businessDir%
call mvn clean

cd %~dp0