#!/bin/sh

########################################
# Dockerコンテナ用のsh
# $1: build/start/stop/check/restart
#   build: コンテナビルド起動
#   start: コンテナ起動
#   stop: コンテナ停止
#   clear: コンテナ削除
#   check: コンテナ状態確認
#   restart: コンテナ再起動
########################################

args_count=$#
if [ ${args_count} -ne 1 ]; then
  echo "引数は必ずbuild/start/stop/check/restartのいずれかを1つ指定してください。"
  exit 1
fi

val=$1
if [ ${val} = "" ]; then
  # 引数が無い場合はstartをデフォルトとする
  ${val}="start"
fi

# 初期化ファイル読み込み
. ./initialize.sh

case ${val} in
  build)
    # コンテナビルド起動
    cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build
    ;;
  start)
    # コンテナ起動
    cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.dev.yml up -d
    ;;
  stop)
    # コンテナ停止
    cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.dev.yml stop
    ;;
  clear)
    # コンテナ削除
    cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.dev.yml down
    ;;
  check)
    # コンテナ状態確認
    cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.dev.yml ps
    ;;
  restart)
    # コンテナ再起動
    cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.dev.yml restart
    ;;
  *)
    echo "不正な引数です。build/start/stop/clear/check/restartのいずれかを指定してください。"
    ;;
esac

exit 0
