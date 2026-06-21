#!/bin/sh

# ----------------------------------------------------------------------------------------
# 健康情報一括登録バッチ
# 
# 概要：指定したディレクトリのJSONファイルから健康情報を登録する。
# 
# 引数：なし
# 
# input: /home/admin/data/healthInfoRegist/*json
# 
# output: MySQL.HEALTH_INFO
# ----------------------------------------------------------------------------------------
set -eu

echo "------------------------------------------------------------------------"
echo "START $0"
echo "------------------------------------------------------------------------"

# call common.sh
. ./common.sh

RUN_ID=$(create_run_id)

# ロック処理
SCRIPT_NAME=$(basename "$0")
LOCK_FILE_NAME="${SCRIPT_NAME}.lock"
LOCK_FILE="${LOCK_DIR}/${LOCK_FILE_NAME}"
mkdir -p "$(dirname "$LOCK_FILE")"

exec 9>"${LOCK_FILE}"

if ! flock -n 9; then
  echo "[ERROR] healthInfoFileRegistBatchJob is already running."
  exit 50
fi

cd ${BASE_DIR} && docker compose \
  --project-directory "${BASE_DIR}" \
  -f ${BASE_DIR}/${DOCKER_DIR}/docker-compose.yml \
  run --rm ha-batch --spring.batch.job.name=healthInfoFileRegistBatchJob \
    "run.id=${RUN_ID}"

echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
