#!/bin/sh

########################################
# mvn-buildを行うshell
########################################

# 基底ディレクトリ
BASE_DIR="/Applications/Eclipse_2019-03.app/Contents/work-3g"
COMMON_DIR=${BASE_DIR}"/ha-common"
DB_DIR=${BASE_DIR}"/ha-db"
WEB_DIR=${BASE_DIR}"/ha-web"
BUSINESS_DIR=${BASE_DIR}"/ha-business"
API_DIR=${BASE_DIR}"/ha-api"
DASHBOARD_DIR=${BASE_DIR}"/ha-dashboard"

# 成果物ディレクトリ
COMMON_TARGET_DIR=${BASE_DIR}"/ha-common/target"
DB_TARGET_DIR=${BASE_DIR}"/ha-db/target"
WEB_TARGET_DIR=${BASE_DIR}"/ha-web/target"
BUSINESS_TARGET_DIR=${BASE_DIR}"/ha-business/target"

# コピー先ディレクトリ
DB_LIB_DIR=${BASE_DIR}"/ha-db/src/main/webapp/WEB-INF/lib"
WEB_LIB_DIR=${BASE_DIR}"/ha-web/src/main/webapp/WEB-INF/lib"
BUSINESS_LIB_DIR=${BASE_DIR}"/ha-business/src/main/webapp/WEB-INF/lib"
API_LIB_DIR=${BASE_DIR}"/ha-api/src/main/webapp/WEB-INF/lib"
DASHBOARD_LIB_DIR=${BASE_DIR}"/ha-dashboard/src/main/webapp/WEB-INF/lib"


# libディレクトリ確認用変数
CHECK_DB_DIR=${BASE_DIR}"/ha-db/src/main/webapp/WEB-INF"
CHECK_WEB_DIR=${BASE_DIR}"/ha-web/src/main/webapp/WEB-INF"
CHECK_BUSINESS_DIR=${BASE_DIR}"/ha-business/src/main/webapp/WEB-INF"
CHECK_API_DIR=${BASE_DIR}"/ha-api/src/main/webapp/WEB-INF"
CHECK_DASHBOARD_DIR=${BASE_DIR}"/ha-dashboard/src/main/webapp/WEB-INF"


echo "--------------------"
echo "START maven-build.sh"
echo "--------------------"

JAR_VERSION="1.0"

########################################
# common jarを作成
########################################
cd ${COMMON_DIR}
mvn package
mvn install:install-file -Dfile=target/ha-common-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=common -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true

########################################
# db jarを作成
########################################
cd ${DB_DIR}
mvn package
mvn install:install-file -Dfile=target/ha-db-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=db -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true

########################################
# web jarを作成
########################################
cd ${BUSINESS_DIR}
mvn package
mvn install:install-file -Dfile=target/ha-web-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=web -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true

########################################
# business jarを作成
########################################
cd ${BUSINESS_DIR}
mvn package
mvn install:install-file -Dfile=target/ha-business-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=business -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


# libディレクトリを確認する
cd ${CHECK_DB_DIR}
if [ ! -e "lib" ] ; then
  mkdir lib
fi

cd ${CHECK_WEB_DIR}
if [ ! -e "lib" ] ; then
  mkdir lib
fi

cd ${CHECK_BUSINESS_DIR}
if [ ! -e "lib" ] ; then
  mkdir lib
fi

cd ${CHECK_API_DIR}
if [ ! -e "lib" ] ; then
  mkdir lib
fi

cd ${CHECK_DASHBOARD_DIR}
if [ ! -e "lib" ] ; then
  mkdir lib
fi

########################################
# deploy common
########################################
cd ${COMMON_TARGET_DIR}
cp *.jar ${DB_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${DB_LIB_DIR}
fi

cp *.jar ${WEB_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${WEB_LIB_DIR}
fi

cp *.jar ${BUSINESS_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${BUSINESS_LIB_DIR}
fi

cp *.jar ${API_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${API_LIB_DIR}
fi

cp *.jar ${DASHBOARD_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${DASHBOARD_LIB_DIR}
fi

########################################
# deploy db
########################################
cd ${DB_TARGET_DIR}
cp *.jar ${BUSINESS_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${BUSINESS_LIB_DIR}
fi

cp *.jar ${WEB_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${WEB_LIB_DIR}
fi

cp *.jar ${API_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${API_LIB_DIR}
fi

cp *.jar ${DASHBOARD_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${DASHBOARD_LIB_DIR}
fi

########################################
# deploy web
########################################
cd ${WEB_TARGET_DIR}
cp *.jar ${API_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${API_LIB_DIR}
fi

cp *.jar ${DASHBOARD_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${DASHBOARD_LIB_DIR}
fi

########################################
# deploy business
########################################
cd ${BUSINESS_TARGET_DIR}
cp *.jar ${WEB_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${WEB_LIB_DIR}
fi

cp *.jar ${API_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${API_LIB_DIR}
fi

cp *.jar ${DASHBOARD_LIB_DIR}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${DASHBOARD_LIB_DIR}
fi


echo "------------------"
echo "END maven-build.sh"
echo "------------------"

cd ${BASE_DIR}"/ha-build/shell"
