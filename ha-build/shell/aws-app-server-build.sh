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
APP_LOG_DIR=$LOG_DIR/app
# ログファイルを定義
DASHBOARD_LOG=dashboard.log

# /var/logへ移動
cd $LOG_DIR
# appディレクトリ作成
sudo mkdir -m 777 app
# appディレクトリへ移動
cd $APP_LOG_DIR
# 空ファイルを作成
touch $DASHBOARD_LOG
# ファイルのパーミッションを変更
chmod 777 $DASHBOARD_LOG

########################################
# 2:システム日時を設定
########################################
# /home/ec2-user/clock_newファイルを/etc/sysconfig/配下にコピーする
sudo cp -i /home/ec2-user/clock /etc/sysconfig/
# 時間帯ファイルにシンボリックリンク
sudo ln -sf /usr/share/zoneinfo/Japan /etc/localtime
