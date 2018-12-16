#!/bin/sh

########################################
# mavenクリーンを行うshell
########################################

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

echo "-------------------"
echo "START maven-clean.sh"
echo "-------------------"

echo "------------------------------------------------------------------------"
echo "START common project clean"
echo "------------------------------------------------------------------------"
cd ${commonTargetDir}
mvn clean
echo "------------------------------------------------------------------------"
echo "END common project clean"
echo "------------------------------------------------------------------------"


echo "------------------------------------------------------------------------"
echo "START db project clean"
echo "------------------------------------------------------------------------"
cd ${dbTargetDir}
mvn clean
echo "------------------------------------------------------------------------"
echo "END db project clean"
echo "------------------------------------------------------------------------"


echo "------------------------------------------------------------------------"
echo "START business project clean"
echo "------------------------------------------------------------------------"
cd ${businessTargetDir}
mvn clean
echo "------------------------------------------------------------------------"
echo "END business project clean"
echo "------------------------------------------------------------------------"

# 元のshellのディレクトリに戻る
cd ${baseDir}"/ha-build/shell"
echo "-------------------"
echo "END maven-clean.sh"
echo "-------------------"
