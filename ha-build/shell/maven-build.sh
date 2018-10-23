#!/bin/sh
# 基底ディレクトリ
baseDir="/Applications/Eclipse_4.8.0.app/Contents/workspace/work-3g"
commonDir=${baseDir}"/ha-common"
dbDir=${baseDir}"/ha-db"
businessDir=${baseDir}"/ha-business"
apiDir=${baseDir}"/ha-api"
webDir=${baseDir}"/ha-web"

# 成果物ディレクトリ
commonTargetDir=${baseDir}"/ha-common/target"
dbTargetDir=${baseDir}"/ha-db/target"
businessTargetDir=${baseDir}"/ha-business/target"

# コピー先ディレクトリ
dbLibDir=${baseDir}"/ha-db/src/main/webapp/WEB-INF/lib"
businessLibDir=${baseDir}"/ha-business/src/main/webapp/WEB-INF/lib"
apiLibDir=${baseDir}"/ha-api/src/main/webapp/WEB-INF/lib"
webLibDir=${baseDir}"/ha-web/src/main/webapp/WEB-INF/lib"

# libディレクトリ確認用変数
checkDbDir=${baseDir}"/ha-db/src/main/webapp/WEB-INF"
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
# db jarを作成
####################
cd ${dbDir}
mvn package
mvn install:install-file -Dfile=target/ha-db-${JAR_VERSION}.jar -DgroupId=jp.co.ha.db -DartifactId=ha-db -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true

####################
# business jarを作成
####################
cd ${businessDir}
mvn package
mvn install:install-file -Dfile=target/ha-business-${JAR_VERSION}.jar -DgroupId=jp.co.ha.business -DartifactId=ha-business -Dversion=${JAR_VERSION} -Dpackaging=jar -DgeneratePom=true

cd ${checkDbDir}
if [ ! -e "lib" ]; then
  mkdir lib
fi

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
cp *.jar ${dbLibDir}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${dbLibDir}
fi
cp *.jar ${businessLibDir}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${businessLibDir}
fi
cp *.jar ${apiLibDir}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${apiLibDir}
fi
cp *.jar ${webLibDir}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${webLibDir}
fi

####################
# deploy db
####################
cd ${dbTargetDir}
cp *.jar ${businessLibDir}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${businessLibDir}
fi
cp *.jar ${apiLibDir}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${apiLibDir}
fi
cp *.jar ${webLibDir}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${webLibDir}
fi

####################
# deploy business
####################
cd ${businessTargetDir}
cp *.jar ${apiLibDir}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${apiLibDir}
fi
cp *.jar ${webLibDir}
if [ $? != "0" ] ; then
  echo "コピーに失敗しました. to : "${webLibDir}
fi


echo "-------------------"
echo "END maven-build.sh"
echo "-------------------"

cd ${baseDir}"/ha-build/shell"