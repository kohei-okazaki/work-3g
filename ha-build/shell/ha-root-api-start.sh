#!/bin/sh

########################################
#
# root api server start
#
########################################

echo "START ${basename}"

# maven build
. ./maven-build.sh local

# root api server start
cd ../../ha-root
mvn spring-boot:run -Dmaven.test.skip=true -Plocal

echo "END ${basename}"