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
  echo "[ERROR] monthlyHealthInfoSummaryBatchJob is already running."
  exit 50
fi

# 処理対象年月YYYYMM
DATE_OPTION_VALUE=$1
RUN_ID=$(create_run_id)

cd ${BASE_DIR} && docker compose \
  --project-directory "${BASE_DIR}" \
  -f ${BASE_DIR}/${DOCKER_DIR}/docker-compose.yml \
  -f ${BASE_DIR}/${DOCKER_DIR}/docker-compose.${ENV}.yml \
  run --rm ha-batch --spring.batch.job.name=monthlyHealthInfoSummaryBatchJob \
    "m=${DATE_OPTION_VALUE}" "run.id=${RUN_ID}"

echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
