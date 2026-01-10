#!/bin/sh

# ----------------------------------------------------------------------------------------
# 健康情報連携バッチ
# 
# 概要：指定した日の健康情報をha-trackへ連携する。
# 
# 引数：YYYYMMDD
# 
# input: MySQL.HEALTH_INFO
# 
# output: DynamoDB.health_info
# ----------------------------------------------------------------------------------------

echo "------------------------------------------------------------------------"
echo "START $0"
echo "------------------------------------------------------------------------"

# call common.sh
. ./common.sh

# 処理対象年日YYYYMMDD
DATE_OPTION_VALUE=$1

cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.${ENV}.yml run --rm ha-batch --spring.batch.job.name=healthInfoMigrateBatchJob d=${DATE_OPTION_VALUE}

echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
