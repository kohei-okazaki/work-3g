#!/bin/sh

# ----------------------------------------------------------------------------------------
# 月次健康情報集計バッチ
# 引数1:処理対象年月(YYYYMM)
# ----------------------------------------------------------------------------------------

print "------------------------------------------------------------------------"
print "START $0"
print "------------------------------------------------------------------------"

# call common.sh
./common.sh

# 処理対象年月YYYYMM
DATE_OPTION_VALUE=$1

cd ${BATCH_DIR}/target
java -jar -Dspring.profiles.active=${ENV} -Dspring.batch.job.names=ｍonthlyHealthInfoSummaryBatchJob ${BATCH_JAR} m=${DATE_OPTION_VALUE}

print "------------------------------------------------------------------------"
print "END $0"
print "------------------------------------------------------------------------"
