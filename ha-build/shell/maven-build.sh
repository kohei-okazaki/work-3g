#!/bin/sh

########################################
# mvn build shell
########################################

# 基底ディレクトリ
BASE_DIR="/Applications/app/git/work-3g"

# プロジェクトディレクトリ
COMMON_DIR=${BASE_DIR}"/ha-common"
DB_DIR=${BASE_DIR}"/ha-db"
BUSINESS_DIR=${BASE_DIR}"/ha-business"
BATCH_DIR=${BASE_DIR}"/ha-batch"
ROOT_DIR=${BASE_DIR}"/ha-root"
API_DIR=${BASE_DIR}"/ha-api"
DASHBOARD_DIR=${BASE_DIR}"/ha-dashboard"

echo "------------------------------------------------------------------------"
echo "START $0"
echo "------------------------------------------------------------------------"

# arg check
if [ $# -ne 1 ]; then
  echo "profile is not found";
  exit 1;
fi

# jar version
JAR_VERSION="1.0.0"
# build profile
PROFILE=$1

########################################
# build common
########################################
cd ${COMMON_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/common-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=common -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


########################################
# build db
########################################
cd ${DB_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/db-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=db -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


########################################
# build business
########################################
cd ${BUSINESS_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/business-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=business -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


########################################
# build batch
########################################
cd ${BATCH_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/batch-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=batch -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


########################################
# build root
########################################
cd ${ROOT_DIR}
mvn clean package -Dmaven.test.skip=true -P${PROFILE}
mvn install:install-file -Dfile=target/root-${JAR_VERSION}.jar -DgroupId=jp.co.ha -DartifactId=root -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


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
echo "END $0"
echo "------------------------------------------------------------------------"
