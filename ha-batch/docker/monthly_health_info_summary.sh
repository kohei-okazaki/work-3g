#!/bin/sh

# ----------------------------------------------------------------------------------------
# 月次健康情報集計バッチ
# 
# 概要：指定した年月の健康情報をCSVファイルに出力し圧縮してS3へ転送する。
#
# 引数：YYYYMM
# 
# input: MySQL.HEALTH_INFO
# 
# output: S3.monthly/healthinfo/year=YYYY/YYYYMMDD.csv.gz
# ----------------------------------------------------------------------------------------

echo "------------------------------------------------------------------------"
echo "START $0"
echo "------------------------------------------------------------------------"

# call common.sh
. ./common.sh

# 処理対象年月YYYYMM
DATE_OPTION_VALUE=$1

cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.${ENV}.yml run --rm ha-batch --spring.batch.job.name=monthlyHealthInfoSummaryBatchJob m=${DATE_OPTION_VALUE}

echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
