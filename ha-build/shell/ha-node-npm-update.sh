#!/bin/sh

########################################
#
# Nodeのモジュールの更新を行う
# package.jsonのdependencies内のversionの最新化
#
########################################

# ha-nodeまで移動
cd ../../ha-node

# nodeモジュールをインストール
npm update
