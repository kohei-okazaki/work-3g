#!/bin/sh
# 基底ディレクトリ
baseDir="/Applications/Eclipse_4.8.0.app/Contents/workspace/work-3g"
commonDir=${baseDir}"/ha-common"
businessDir=${baseDir}"/ha-business"
apiDir=${baseDir}"/ha-api"
webDir=${baseDir}"/ha-web"

# 成果物ディレクトリ
commonTargetDir=${baseDir}"/ha-common/target"
businessTargetDir=${baseDir}"/ha-business/target"

# コピー先ディレクトリ
businessLibDir=${baseDir}"/ha-business/src/main/webapp/WEB-INF/lib"
apiLibDir=${baseDir}"/ha-api/src/main/webapp/WEB-INF/lib"
webLibDir=${baseDir}"/ha-web/src/main/webapp/WEB-INF/lib"

# libディレクトリ確認用変数
checkBusinessDir=${baseDir}"/ha-business/src/main/webapp/WEB-INF"
checkApiDir=${baseDir}"/ha-api/src/main/webapp/WEB-INF"
checkWebDir=${baseDir}"/ha-web/src/main/webapp/WEB-INF"

echo "-------------------"
echo "START maven-build.sh"
echo "-------------------"

JAR_VERSION="1.0"

####################
# common jarを作成
####################
cd ${commonDir}
mvn package
mvn install:install-file -Dfile=target/ha-common-${JAR_VERSION}.jar -DgroupId=jp.co.ha.common -DartifactId=ha-common -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true

####################
# business jarを作成
####################
cd ${businessDir}
mvn package
mvn install:install-file -Dfile=target/ha-business-${JAR_VERSION}.jar -DgroupId=jp.co.ha.business -DartifactId=ha-business -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true


cd ${checkBusinessDir}
if [ ! -e "lib" ]; then
  mkdir lib
fi

cd ${checkApiDir}
if [ ! -e "lib" ]; then
  mkdir lib
fi

cd ${checkWebDir}
if [ ! -e "lib" ]; then
  mkdir lib
fi

####################
# deploy common
####################
cd ${commonTargetDir}
cp *.jar ${businessLibDir}
cp *.jar ${apiLibDir}
cp *.jar ${webLibDir}

####################
# deploy business
####################
cd ${businessTargetDir}
cp *.jar ${apiLibDir}
cp *.jar ${webLibDir}


echo "-------------------"
echo "END maven-build.sh"
echo "-------------------"

cd ${baseDir}"/ha-build/shell"