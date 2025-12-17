#!/bin/sh

########################################
# Dockerコンテナ起動用のsh(ha-api)
########################################

# 初期化ファイル読み込み
. ./initialize.sh

# コンテナ起動
cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build ha-api
