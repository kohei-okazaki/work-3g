#!/bin/sh
#####################################
# Dashboard server setup.sh
#####################################

#####################################
# define
#####################################
LOG_DIR=/var/log
APP_LOG_DIR=$LOG_DIR/app
DASHBOARD_LOG=dashboard.log
APP_BASE_DIR=/home/admin
APP_DATA_DIR=$APP_BASE_DIR/data
HEALTH_INFO_REF_DIR=$APP_DATA_DIR/healthInfoReference

##########################################################################
# 1 log setup
##########################################################################
cd $LOG_DIR
sudo mkdir -m 777 app
cd $APP_LOG_DIR
touch $DASHBOARD_LOG
chmod 777 $DASHBOARD_LOG

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
