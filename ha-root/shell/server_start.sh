#!/bin/sh
##########################################################################
# boot server start
##########################################################################

echo "START ${basename}"

# maven build
cd ../../ha-build/shell
. ./maven-build.sh local

# server start
cd ../../ha-root
mvn spring-boot:run -Dmaven.test.skip=true -Plocal

echo "END ${basename}"