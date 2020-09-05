#!/bin/sh

########################################
# mvn-buildを行うshell
########################################

# 基底ディレクトリ
BASE_DIR="/Applications/Eclipse_2019-03.app/Contents/work-3g"

# プロジェクトディレクトリ
COMMON_DIR=${BASE_DIR}"/ha-common"
DB_DIR=${BASE_DIR}"/ha-db"
WEB_DIR=${BASE_DIR}"/ha-web"
BUSINESS_DIR=${BASE_DIR}"/ha-business"
BATCH_DIR=${BASE_DIR}"/ha-build"
API_DIR=${BASE_DIR}"/ha-api"
DASHBOARD_DIR=${BASE_DIR}"/ha-dashboard"

echo "------------------------------------------------------------------------"
echo "START ${basename}"
echo "------------------------------------------------------------------------"

# jar version
JAR_VERSION="1.0.0"
# build profile
PROFILE="ec2"

########################################
# build common
########################################
cd ${COMMON_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/ha-common-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=common -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


########################################
# build db
########################################
cd ${DB_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/ha-db-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=db -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


########################################
# build web
########################################
cd ${WEB_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/ha-web-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=web -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


########################################
# build business
########################################
cd ${BUSINESS_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/ha-business-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=business -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


########################################
# build batch
########################################
cd ${BATCH_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/ha-batch-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=batch -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


########################################
# build api
########################################
cd ${API_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}


########################################
# build dashboard
########################################
cd ${DASHBOARD_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}


# 元のshellのディレクトリに戻る
cd ${BASE_DIR}"/ha-build/shell"

echo "------------------------------------------------------------------------"
echo "END ${basename}"
echo "------------------------------------------------------------------------"
