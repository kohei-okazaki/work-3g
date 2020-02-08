#!/bin/sh

########################################
# AWS環境のアプリサーバのビルド用のsh
#
# 1:ログファイルを用意
# 2:システム日時を設定
#
########################################

########################################
# 1:ログファイルを用意
########################################
# ログディレクトリを定義
LOG_DIR=/var/log
APP_LOG_DIR=/var/log/app
# ログファイルを定義
DASHBOARD_LOG=dashboard.log

# Rootユーザに変更
sudo su - root
# /var/logへ移動
cd ${LOG_DIR}
# appディレクトリ作成
mkdir -m 777 app
# appディレクトリへ移動
cd ${APP_LOG_DIR}
# 空ファイルを作成
touch ${DASHBOARD_LOG}
# ファイルのパーミッションを変更
chmod 777 ${DASHBOARD_LOG}

########################################
# 2:システム日時を設定
########################################
# ユーザをec2-userに変更
sudo su - ec2-user
# /home/ec2-user/clock_newファイルを/etc/sysconfig/配下にコピーする
sudo -u root cp -i /home/ec2-user/clock_new /etc/sysconfig/
