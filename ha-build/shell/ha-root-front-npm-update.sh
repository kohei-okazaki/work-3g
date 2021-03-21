#!/bin/sh

########################################
#
# Nodeのモジュールの更新を行う
# package.jsonのdependencies内のversionの最新化
#
########################################

# ha-root/frontまで移動
cd ../../ha-root/front

# nodeモジュールをインストール
npm update
