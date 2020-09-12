#!/bin/sh
##########################################################################
# Dashboard server setup.sh
##########################################################################

##########################################################################
# define
##########################################################################
LOG_DIR=/var/log
APP_LOG_DIR=$LOG_DIR/app
APP_BASE_DIR=/home/admin
APP_DATA_DIR=$APP_BASE_DIR/data
HEALTH_INFO_REF_DIR=$APP_DATA_DIR/healthInfoReference

##########################################################################
# 1 log setup
##########################################################################
cd $LOG_DIR
sudo mkdir -m 777 app

##########################################################################
# 2 sysdate setup
##########################################################################
sudo cp -i /home/ec2-user/clock /etc/sysconfig/
sudo ln -sf /usr/share/zoneinfo/Japan /etc/localtime

##########################################################################
# 3 app base dir create
##########################################################################
# create /home/admin
cd /home
sudo mkdir -m 777 $APP_BASE_DIR
# create /home/admin/data
cd $APP_BASE_DIR
sudo mkdir -m 777 $APP_DATA_DIR
# create /home/admin/data/healthInfoReference
cd $APP_DATA_DIR
sudo mkdir -m 777 $HEALTH_INFO_REF_DIR
