#!/bin/sh

########################################
# Dockerコンテナ起動用のsh(ha-root-api)
########################################

# 初期化ファイル読み込み
. ./common.sh

# コンテナ起動
cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.local.yml up --build ha-root-api
