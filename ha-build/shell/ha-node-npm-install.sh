#!/bin/sh

########################################
#
# Nodeのモジュールのインストールを行う
# node_modulesとpackage-lock.jsonを作成
#
########################################

# ha-nodeまで移動
cd ../../ha-node

# nodeモジュールをインストール
npm install
