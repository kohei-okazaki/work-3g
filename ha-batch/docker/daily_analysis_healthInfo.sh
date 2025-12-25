#!/bin/sh

# ----------------------------------------------------------------------------------------
# 日次健康情報データ分析連携バッチ
# 
# 概要：指定した年月日の健康情報をCSVファイルに出力し圧縮してS3へ転送する。
#
# 引数：YYYYMMDD
# 
# input: MySQL-HEALTH_INFO
# 
# output: S3.analysis/YYYYMMDD/healthinfo.csv.gz
# ----------------------------------------------------------------------------------------

echo "------------------------------------------------------------------------"
echo "START $0"
echo "------------------------------------------------------------------------"

# call common.sh
. ./common.sh

# 処理対象年月YYYYMMDD
DATE_OPTION_VALUE=$1

# 結果チェック関数
# 引数：ジョブ名、終了コード
function check_result(name, result_code) {
  if [ ${result_code} -ne 0 ]; then
    echo "$name failed with exit code $result_code"
  fi
  exit $result_code
}

cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.${ENV}.yml run --rm ha-batch --spring.batch.job.name=dailyHealthInfoJob d=${DATE_OPTION_VALUE}
result=$?
check_result "dailyHealthInfoJob" ${result}


echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
