#!/bin/sh

########################################
# Dockerコンテナ起動用のsh(MySQL)
########################################
set -eu

# 初期化ファイル読み込み
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
. "${SCRIPT_DIR}/common.sh"

# ------------------------------------------------------------------------------------------------------------
# 定数定義
# ------------------------------------------------------------------------------------------------------------
# 基底compose.ymlファイルパス
BASE_DOCKER_COMPOSE_FILE_PATH="$BASE_DIR/$DOCKER_COMPOSE_FILE_PATH"
# コンテナ起動
docker compose --project-directory "$BASE_DIR" -f "$BASE_DOCKER_COMPOSE_FILE_PATH" up -d mysql
