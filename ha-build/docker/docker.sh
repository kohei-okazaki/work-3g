#!/bin/bash

######################################################################################################################
# Dockerコンテナ用のsh
# $1: build/start/stop/clear/check/restart
#   build: コンテナビルド起動
#   start: コンテナ起動
#   stop: コンテナ停止
#   clear: コンテナ削除
#   check: コンテナ状態確認
#   restart: コンテナ再起動
######################################################################################################################
set -euo pipefail

args_count=$#
if [ ${args_count} -ne 1 ]; then
  echo "引数は必ずbuild/start/stop/clear/check/restartのいずれかを1つ指定してください。"
  exit 1
fi

# 初期化ファイル読み込み
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
. "${SCRIPT_DIR}/common.sh"

# ------------------------------------------------------------------------------------------------------------
# 定数定義
# ------------------------------------------------------------------------------------------------------------
# 基底compose.ymlファイルパス
BASE_DOCKER_COMPOSE_FILE_PATH="$BASE_DIR/$DOCKER_COMPOSE_FILE_PATH"
# ローカル用compose.yml
LOCAL_DOCKER_COMPOSE_FILE_PATH="$BASE_DIR/$DOCKER_COMPOSE_LOCAL_FILE_PATH"

args="$1"
case ${args} in
  build)
    # コンテナビルド起動
    docker compose --project-directory "$BASE_DIR" -f "$BASE_DOCKER_COMPOSE_FILE_PATH" -f "$LOCAL_DOCKER_COMPOSE_FILE_PATH" build ha-batch
    docker compose --project-directory "$BASE_DIR" -f "$BASE_DOCKER_COMPOSE_FILE_PATH" -f "$LOCAL_DOCKER_COMPOSE_FILE_PATH" up -d mysql ha-api ha-dashboard ha-root-api ha-root-front ha-track
    ;;
  start)
    # コンテナ起動
    docker compose --project-directory "$BASE_DIR" -f "$BASE_DOCKER_COMPOSE_FILE_PATH" -f "$LOCAL_DOCKER_COMPOSE_FILE_PATH" up -d mysql ha-api ha-dashboard ha-root-api ha-root-front ha-track
    ;;
  stop)
    # コンテナ停止
    docker compose --project-directory "$BASE_DIR" -f "$BASE_DOCKER_COMPOSE_FILE_PATH" -f "$LOCAL_DOCKER_COMPOSE_FILE_PATH" stop
    ;;
  clear)
    # コンテナ削除
    docker compose --project-directory "$BASE_DIR" -f "$BASE_DOCKER_COMPOSE_FILE_PATH" -f "$LOCAL_DOCKER_COMPOSE_FILE_PATH" down
    ;;
  check)
    # コンテナ状態確認
    docker compose --project-directory "$BASE_DIR" -f "$BASE_DOCKER_COMPOSE_FILE_PATH" -f "$LOCAL_DOCKER_COMPOSE_FILE_PATH" ps
    ;;
  restart)
    # コンテナ再起動
    docker compose --project-directory "$BASE_DIR" -f "$BASE_DOCKER_COMPOSE_FILE_PATH" -f "$LOCAL_DOCKER_COMPOSE_FILE_PATH" restart mysql ha-api ha-dashboard ha-root-api ha-root-front ha-track
    ;;
  *)
    echo "不正な引数です。build/start/stop/clear/check/restartのいずれかを指定してください。"
    ;;
esac

exit 0
