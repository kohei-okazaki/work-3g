#!/bin/sh

# ----------------------------------------------------------------------------------------
# 日次データ分析連携JobSet
# 
# 概要：指定した年月日の健康情報/ユーザ情報/API通信ログ/バッチ実行ログをCSVファイルに出力し圧縮してS3へ転送するための各子シェルでジョブを実行する。
#       dailyAnalysisHealthInfo.sh
#       dailyAnalysisUser.sh
#       dailyAnalysisApiLog.sh
#       dailyAnalysisBatchLog.sh
#
# 引数：YYYYMMDD
# 
# input: MySQL-HEALTH_INFO
#        MySQL-USER
#        DynamoDB-API_LOG
#        DynamoDB-BATCH_LOG
#
# output: S3.analysis/YYYYMMDD/healthinfo.csv.gz
#         S3.analysis/YYYYMMDD/user.csv.gz
#         S3.analysis/YYYYMMDD/api_log.csv.gz
#         S3.analysis/YYYYMMDD/batch_log.csv.gz
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

# 日次健康情報データ分析連携バッチ
daily_analysis_healthInfo.sh ${DATE_OPTION_VALUE}
result=$?
check_result "dailyHealthInfoJob" ${result}

# 日次ユーザ情報データ分析連携バッチ
daily_analysis_user.sh ${DATE_OPTION_VALUE}
result=$?
check_result "dailyUserInfoJob" ${result}

# 日次API通信ログデータ分析連携バッチ
# cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.${ENV}.yml run --rm ha-batch --spring.batch.job.name=dailyApiLogJob d=${DATE_OPTION_VALUE}
daily_analysis_api_log.sh ${DATE_OPTION_VALUE}
result=$?
check_result "dailyApiLogJob" ${result}

# 日次バッチ実行ログデータ分析連携バッチ
daily_analysis_batch_log.sh ${DATE_OPTION_VALUE}
result=$?
check_result "dailyBatchLogJob" ${result}

echo "------------------------------------------------------------------------"
echo "END $0"
echo "------------------------------------------------------------------------"
