#!/bin/sh

# ----------------------------------------------------------------------------------------
# AWS SQS取込バッチ
# 
# 概要：SQSからメッセージを取得し、以下のテーブルに登録する。
#      ・API_LOG
# 
# 引数：なし
# 
# input: SQS.${env}.api_log_queue.fifo
# 
# output: MySQL.API_LOG
# ----------------------------------------------------------------------------------------

echo "------------------------------------------------------------------------"
echo "START $0"
echo "------------------------------------------------------------------------"

# call common.sh
. ./common.sh

LOCK_FILE="/var/lock/aws_sqs_import.lock"
mkdir -p "$(dirname "$LOCK_FILE")"

exec 9>"${LOCK_FILE}"

if ! flock -n 9; then
  echo "[ERROR] awsSqsImportBatchJob is already running."
  exit 50
fi

cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.${ENV}.yml run --rm ha-batch --spring.batch.job.name=awsSqsImportBatchJob

echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
