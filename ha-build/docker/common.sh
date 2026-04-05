#!/bin/sh

########################################
# common.sh
########################################
set -eu

env_val="${1:-local}"
case "$env_val" in
  local|dev1) ;;
  *)
    echo "不正な環境です: $env_val"
    exit 1
    ;;
esac

# read const.txt
FILE_NAME="const_${env_val}.txt"
. ./${FILE_NAME}
