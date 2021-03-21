#!/bin/sh

########################################
#
# Nodeのモジュールのインストールを行う
# node_modulesとpackage-lock.jsonを作成
#
########################################

# ha-root/frontまで移動
cd ../../ha-root/front

# nodeモジュールをインストール
npm install
