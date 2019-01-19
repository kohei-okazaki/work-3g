#!/bin/sh

########################################
# mvn-cleanを行うshell
########################################

# 基底ディレクトリ
baseDir="/Applications/Eclipse_4.8.0.app/Contents/workspace/work-3g"

# プロジェクトディレクトリ
commonDir=${baseDir}"/ha-common"
dbDir=${baseDir}"/ha-db"
businessDir=${baseDir}"/ha-business"

echo "-------------------"
echo "START maven-clean.sh"
echo "-------------------"

echo "------------------------------------------------------------------------"
echo "START common project clean"
echo "------------------------------------------------------------------------"
cd ${commonDir}
mvn clean
echo "------------------------------------------------------------------------"
echo "END common project clean"
echo "------------------------------------------------------------------------"


echo "------------------------------------------------------------------------"
echo "START db project clean"
echo "------------------------------------------------------------------------"
cd ${dbDir}
mvn clean
echo "------------------------------------------------------------------------"
echo "END db project clean"
echo "------------------------------------------------------------------------"


echo "------------------------------------------------------------------------"
echo "START business project clean"
echo "------------------------------------------------------------------------"
cd ${businessDir}
mvn clean
echo "------------------------------------------------------------------------"
echo "END business project clean"
echo "------------------------------------------------------------------------"

# 元のshellのディレクトリに戻る
cd ${baseDir}"/ha-build/shell"
echo "-------------------"
echo "END maven-clean.sh"
echo "-------------------"
