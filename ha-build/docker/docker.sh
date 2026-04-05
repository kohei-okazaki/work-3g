#!/bin/sh

######################################################################################################################
# Dockerコンテナ用のsh
#
# $1: local/dev1
#   local: ローカル環境
#   dev1: dev1環境
#
# $2: build/start/stop/clear/check/restart
#   build: コンテナビルド起動
#   start: コンテナ起動
#   stop: コンテナ停止
#   clear: コンテナ削除
#   check: コンテナ状態確認
#   restart: コンテナ再起動
######################################################################################################################

args_count=$#
if [ ${args_count} -ne 2 ]; then
  echo "第一引数は必ず local/dev1 のいずれかを1つ指定してください。"
  echo "第二引数は必ず build/start/stop/clear/check/restart のいずれかを1つ指定してください。"
  exit 1
fi

env_val=$1
if [ "${env_val}" = "" ]; then
  # 引数が無い場合はlocalをデフォルトとする
  env_val="local"
fi

operation_val=$2
if [ "${operation_val}" = "" ]; then
  # 引数が無い場合はstartをデフォルトとする
  operation_val="start"
fi

# 初期化ファイル読み込み
. ./common.sh ${env_val}

case ${operation_val} in
  build)
    # コンテナビルド起動
    cd ${BASE_DIR} && docker compose -f ${COMMON_DOCKER_FILE} -f ${ENV_DOCKER_FILE} build ha-batch
    cd ${BASE_DIR} && docker compose -f ${COMMON_DOCKER_FILE} -f ${ENV_DOCKER_FILE} up -d mysql mongo ha-api ha-dashboard ha-root-api ha-root-front ha-track
    ;;
  start)
    # コンテナ起動
    cd ${BASE_DIR} && docker compose -f ${COMMON_DOCKER_FILE} -f ${ENV_DOCKER_FILE} up -d mysql mongo ha-api ha-dashboard ha-root-api ha-root-front ha-track
    ;;
  stop)
    # コンテナ停止
    cd ${BASE_DIR} && docker compose -f ${COMMON_DOCKER_FILE} -f ${ENV_DOCKER_FILE} stop
    ;;
  clear)
    # コンテナ削除
    cd ${BASE_DIR} && docker compose -f ${COMMON_DOCKER_FILE} -f ${ENV_DOCKER_FILE} down
    ;;
  check)
    # コンテナ状態確認
    cd ${BASE_DIR} && docker compose -f ${COMMON_DOCKER_FILE} -f ${ENV_DOCKER_FILE} ps
    ;;
  restart)
    # コンテナ再起動
    cd ${BASE_DIR} && docker compose -f ${COMMON_DOCKER_FILE} -f ${ENV_DOCKER_FILE} restart mysql mongo ha-api ha-dashboard ha-root-api ha-root-front ha-track
    ;;
  *)
    echo "不正な引数です。build/start/stop/clear/check/restartのいずれかを指定してください。"
    ;;
esac

exit 0
