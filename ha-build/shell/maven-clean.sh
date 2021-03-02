#!/bin/sh

########################################
# mvn clean shell
########################################

# 基底ディレクトリ
BASE_DIR="/Applications/app/git/work-3g"

# プロジェクトディレクトリ
COMMON_DIR=${BASE_DIR}"/ha-common"
DB_DIR=${BASE_DIR}"/ha-db"
WEB_DIR=${BASE_DIR}"/ha-web"
BUSINESS_DIR=${BASE_DIR}"/ha-business"
BATCH_DIR=${BASE_DIR}"/ha-batch"
API_DIR=${BASE_DIR}"/ha-api"
DASHBOARD_DIR=${BASE_DIR}"/ha-dashboard"

echo "------------------------------------------------------------------------"
echo "START ${basename}"
echo "------------------------------------------------------------------------"

echo "------------------------------------------------------------------------"
echo "START common project clean"
echo "------------------------------------------------------------------------"
cd ${COMMON_DIR}
mvn clean
echo "------------------------------------------------------------------------"
echo "END common project clean"
echo "------------------------------------------------------------------------"


echo "------------------------------------------------------------------------"
echo "START db project clean"
echo "------------------------------------------------------------------------"
cd ${DB_DIR}
mvn clean
echo "------------------------------------------------------------------------"
echo "END db project clean"
echo "------------------------------------------------------------------------"

echo "------------------------------------------------------------------------"
echo "START web project clean"
echo "------------------------------------------------------------------------"
cd ${WEB_DIR}
mvn clean
echo "------------------------------------------------------------------------"
echo "END web project clean"
echo "------------------------------------------------------------------------"

echo "------------------------------------------------------------------------"
echo "START business project clean"
echo "------------------------------------------------------------------------"
cd ${BUSINESS_DIR}
mvn clean
echo "------------------------------------------------------------------------"
echo "END business project clean"
echo "------------------------------------------------------------------------"

echo "------------------------------------------------------------------------"
echo "START batch project clean"
echo "------------------------------------------------------------------------"
cd ${BATCH_DIR}
mvn clean
echo "------------------------------------------------------------------------"
echo "END batch project clean"
echo "------------------------------------------------------------------------"

echo "------------------------------------------------------------------------"
echo "START api project clean"
echo "------------------------------------------------------------------------"
cd ${API_DIR}
mvn clean
echo "------------------------------------------------------------------------"
echo "END api project clean"
echo "------------------------------------------------------------------------"

echo "------------------------------------------------------------------------"
echo "START dashboard project clean"
echo "------------------------------------------------------------------------"
cd ${DASHBOARD_DIR}
mvn clean
echo "------------------------------------------------------------------------"
echo "END dashboard project clean"
echo "------------------------------------------------------------------------"

# 元のshellのディレクトリに戻る
cd ${BASE_DIR}"/ha-build/shell"

echo "------------------------------------------------------------------------"
echo "END ${basename}"
echo "------------------------------------------------------------------------"
