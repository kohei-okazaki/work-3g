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

# 処理対象年日YYYYMMDD
DATE_OPTION_VALUE=$1

cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.${ENV}.yml run --rm ha-batch --spring.batch.job.name=healthInfoMigrateBatchJob d=${DATE_OPTION_VALUE}

echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
