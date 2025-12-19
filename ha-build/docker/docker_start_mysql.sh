#!/bin/sh

########################################
# Dockerコンテナ起動用のsh(MySQL)
########################################

# 初期化ファイル読み込み
. ./common.sh

# コンテナ起動
cd ${BASE_DIR} && docker compose -f docker-compose.yml -f docker-compose.local.yml up --build mysql
