#!/bin/sh
#####################################
# API server setup.sh
#####################################

#####################################
# define
#####################################
LOG_DIR=/var/log
APP_LOG_DIR=$LOG_DIR/app
API_LOG=api.log

##########################################################################
# 1 log setup
##########################################################################
cd $LOG_DIR
sudo mkdir -m 777 app
cd $APP_LOG_DIR
touch $API_LOG
chmod 777 $API_LOG

##########################################################################
# 2 sysdate setup
##########################################################################
sudo cp -i /home/ec2-user/clock /etc/sysconfig/
sudo ln -sf /usr/share/zoneinfo/Japan /etc/localtime