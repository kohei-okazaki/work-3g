#!/bin/sh

# ----------------------------------------------------------------------------------------
# 日次バッチ実行ログデータ分析連携バッチ
# 
# 概要：指定した年月日のバッチ実行ログをCSVファイルに出力し圧縮してS3へ転送する。
#
# 引数：YYYYMMDD
# 
# input: DynamoDB-batch_log
# 
# output: S3.analysis/YYYYMMDD/batch_log.csv.gz
# ----------------------------------------------------------------------------------------

echo "------------------------------------------------------------------------"
echo "START $0"
echo "------------------------------------------------------------------------"

# call common.sh
. ./common.sh

# 処理対象年月YYYYMMDD
DATE_OPTION_VALUE=$1

# 結果チェック関数
# 引数1：ジョブ名
# 引数2：終了コード
check_result() {
  job_name=$1
  result_code=$2
  if [ ${result_code} -ne 0 ]; then
    echo "$job_name failed with exit code $result_code"
    exit $result_code
  fi
}

cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.${ENV}.yml run --rm ha-batch --spring.batch.job.name=dailyBatchLogJob d=${DATE_OPTION_VALUE}
result=$?
check_result "dailyBatchLogJob" ${result}


echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
