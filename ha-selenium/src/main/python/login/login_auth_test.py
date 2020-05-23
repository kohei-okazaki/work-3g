# -*- coding:utf-8 -*-
'''
Created on 2020/05/23

ログイン処理をテストするPython
@version: 1.0.0
'''

from selenium import webdriver

from src.main.python.common import const_data
from src.main.python.common.logger import Logger
from src.main.python.login.login_auth import LoginAuth

# ここからメイン処理

log = Logger("selenium.log")
log.write("login_auth_test開始")

# driverを取得
driver = webdriver.Chrome(const_data.SELENIUM_DRIVER_PATH)

# 正しいログイン情報でログイン
userId = "master"
password = "master"
LoginAuth(driver, userId, password).doLogin()

# ブラウザバック
driver.back()

# 正しくないパスワードでログイン
userId = "master"
password = "hoge"
LoginAuth(driver, userId, password).doLogin()

log.write("login_auth_test終了")

# ここまでメイン処理
