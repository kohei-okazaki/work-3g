#!/bin/sh

########################################
#
# 管理者用サイト(Front)のローカルサーバを起動
#
########################################

# ha-root/frontまで移動
cd ../../ha-root/front

# npmよりサーバ起動
npm run dev
