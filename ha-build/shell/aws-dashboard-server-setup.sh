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
