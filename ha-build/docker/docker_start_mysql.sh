#!/bin/sh

########################################
# Dockerコンテナ起動用のsh(MySQL)
########################################

# 初期化ファイル読み込み
. ./initialize.sh

# コンテナ起動
cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build mysql
