#!/bin/sh

# ----------------------------------------------------------------------------------------
# 健康情報連携バッチ
# 引数1:処理対象日(YYYYMMDD)
# ----------------------------------------------------------------------------------------

print "------------------------------------------------------------------------"
print "START $0"
print "------------------------------------------------------------------------"

# call common.sh
./common.sh

# 処理対象年月YYYYMM
DATE_OPTION_VALUE=$1

cd ${BATCH_DIR}/target
java -jar -Dspring.profiles.active=${ENV} -Dspring.batch.job.name=healthInfoMigrateBatchJob ${BATCH_JAR} d=${DATE_OPTION_VALUE}

print "------------------------------------------------------------------------"
print "END $0"
print "------------------------------------------------------------------------"
