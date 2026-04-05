#!/bin/sh

# ----------------------------------------------------------------------------------------
# ヘルスチェックバッチ
# 
# 概要：ha-api, ha-root(api)のヘルスチェックを実行する。
# 
# 引数：なし
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

cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.${ENV}.yml run --rm ha-batch --spring.batch.job.name=healthCheckBatchJob

echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
