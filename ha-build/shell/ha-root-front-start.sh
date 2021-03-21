#!/bin/sh

########################################
#
# root front server start
#
########################################

# ha-root/frontまで移動
cd ../../ha-root/front

# npmよりサーバ起動
npm run dev
