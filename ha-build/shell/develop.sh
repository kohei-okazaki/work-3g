#!/bin/sh

########################################
# 開発環境整備用のsh
########################################

# 基底ディレクトリ
BASE_DIR="/Applications/Eclipse_4.8.0.app/Contents/workspace/work-3g"

# ------------------------------------------------------------------------
# START develop.sh
# ------------------------------------------------------------------------


# -----------------------------------------------------------------------
# db
# -----------------------------------------------------------------------
# ha-db/src/main/webapp/WEB-INFを用意
DIRECTORY=${BASE_DIR}"/ha-db/src/main/webapp"
if [ ! -e ${DIRECTORY} ]; then mkdir ${DIRECTORY} ; fi

DIRECTORY=${BASE_DIR}"/ha-db/src/main/webapp/WEB-INF"
if [ ! -e ${DIRECTORY} ]; then mkdir ${DIRECTORY} ; fi


# -----------------------------------------------------------------------
# web
# -----------------------------------------------------------------------
# ha-web/src/main/webapp/WEB-INFを用意
DIRECTORY=${BASE_DIR}"/ha-web/src/main/webapp"
if [ ! -e ${DIRECTORY} ]; then mkdir ${DIRECTORY} ; fi

DIRECTORY=${BASE_DIR}"/ha-web/src/main/webapp/WEB-INF"
if [ ! -e ${DIRECTORY} ]; then mkdir ${DIRECTORY} ; fi


# -----------------------------------------------------------------------
# business
# -----------------------------------------------------------------------
# ha-business/src/main/webapp/WEB-INFを用意
DIRECTORY=${BASE_DIR}"/ha-business/src/main/webapp"
if [ ! -e ${DIRECTORY} ]; then mkdir ${DIRECTORY} ; fi

DIRECTORY=${BASE_DIR}"/ha-business/src/main/webapp/WEB-INF"
if [ ! -e ${DIRECTORY} ]; then mkdir ${DIRECTORY} ; fi




# ------------------------------------------------------------------------
# END develop.sh
# ------------------------------------------------------------------------

cd ${BASE_DIR}"/ha-build/shell"