#!/bin/sh
##########################################################################
# vue app start
##########################################################################

echo "START ${basename}"

cd ../
npm run build:stg

echo "END ${basename}"