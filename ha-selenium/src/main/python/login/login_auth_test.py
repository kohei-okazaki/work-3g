# -*- coding:utf-8 -*-
'''
Created on 2020/05/23

ログイン処理をテストするPython
@version: 1.0.0
'''

from src.main.python.common.logger import Logger
from src.main.python.common.selenium_driver import SeleniumDriver
from src.main.python.login.login_auth import LoginAuth
from src.main.python.login.login_form import LoginForm

# ここからメイン処理
log = Logger("selenium.log")
log.write("login_auth_test開始")

# driverを取得
driver = SeleniumDriver().getDriver()

# 正しいログイン情報でログイン
login_form = LoginForm({
  "user_id": "master",
  "password": "master",
})
LoginAuth(driver).doLogin(login_form)

# ブラウザバック
driver.back()

# 正しいログイン情報でログイン
login_form = LoginForm({
  "user_id": "master",
  "password": "master",
})
LoginAuth(driver).doLogin(login_form)

# ログアウトボタン押下
LoginAuth(driver).doLogout()

# 正しくないパスワードでログイン
login_form = LoginForm({
  "user_id": "master",
  "password": "hoge",
})
LoginAuth(driver).doLogin(login_form)

# 存在しないユーザIDでログイン
login_form = LoginForm({
  "user_id": "tejamvpose4jt4ebtjos",
  "password": "tejamvpose4jt4ebtjos",
})
LoginAuth(driver).doLogin(login_form)

log.write("login_auth_test終了")

exit
# ここまでメイン処理
