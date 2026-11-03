#!/bin/bash

########################################
# init file
########################################
set -eu

# read const.txt
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
. "${SCRIPT_DIR}/const.txt"
