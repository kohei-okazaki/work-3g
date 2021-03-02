#!/bin/sh
##########################################################################
# boot server start
##########################################################################

echo "START ${basename}"

cd ../
mvn spring-boot:run -Dmaven.test.skip=true -Plocal

echo "END ${basename}"