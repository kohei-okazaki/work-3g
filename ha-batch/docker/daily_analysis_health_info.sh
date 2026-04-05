#!/bin/sh

# ----------------------------------------------------------------------------------------
# 日次健康情報データ分析連携バッチ
# 
# 概要：指定した年月日の健康情報をCSVファイルに出力し圧縮してS3へ転送する。
#
# 引数：YYYYMMDD
# 
# input: MySQL.HEALTH_INFO
# 
# output: S3.analysis/YYYYMMDD/healthinfo.csv.gz
# ----------------------------------------------------------------------------------------
set -eu

echo "------------------------------------------------------------------------"
echo "START $0"
echo "------------------------------------------------------------------------"

# call common.sh
. ./common.sh

# ロック処理
SCRIPT_NAME=$(basename "$0")
LOCK_FILE_NAME="${SCRIPT_NAME}.lock"
LOCK_FILE="${LOCK_DIR}/${LOCK_FILE_NAME}"
mkdir -p "$(dirname "$LOCK_FILE")"

exec 9>"${LOCK_FILE}"

if ! flock -n 9; then
  echo "[ERROR] dailyApiLogJob is already running."
  exit 50
fi

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

cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.${ENV}.yml run --rm ha-batch --spring.batch.job.name=dailyHealthInfoJob d=${DATE_OPTION_VALUE}
result=$?
check_result "dailyHealthInfoJob" ${result}

echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"

exit 0
