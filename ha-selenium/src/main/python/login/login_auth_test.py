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

# N001:正しいログイン情報でログイン
login_form = LoginForm({
  "user_id": "master",
  "password": "master",
})
LoginAuth(driver).doLogin(login_form)

# ブラウザバック
driver.back()

# N002:正しいログイン情報でログイン
login_form = LoginForm({
  "user_id": "master",
  "password": "master",
})
LoginAuth(driver).doLogin(login_form)

# ログアウトボタン押下
LoginAuth(driver).doLogout()

# E001:正しくないパスワードでログイン
login_form = LoginForm({
  "user_id": "master",
  "password": "hoge",
})
LoginAuth(driver).doLogin(login_form)

# E002:存在しないユーザIDでログイン
login_form = LoginForm({
  "user_id": "tejamvpose4jt4ebtjos",
  "password": "tejamvpose4jt4ebtjos",
})
LoginAuth(driver).doLogin(login_form)

# 入力チェック
# E003:ユーザIDのみを未入力でログイン
login_form = LoginForm({
  "user_id": "",
  "password": "tejamvpose4jt4ebtjos",
})
LoginAuth(driver).doLogin(login_form)

# E004:パスワードのみを未入力でログイン
login_form = LoginForm({
  "user_id": "master",
  "password": "",
})
LoginAuth(driver).doLogin(login_form)

# E005:ユーザIDのみを最小桁 - 1桁でログイン
login_form = LoginForm({
  "user_id": "1",
  "password": "tejamvpose4jt4ebtjos",
})
LoginAuth(driver).doLogin(login_form)

# E006:パスワードのみを最小桁 - 1桁でログイン
login_form = LoginForm({
  "user_id": "master",
  "password": "1",
})
LoginAuth(driver).doLogin(login_form)

# E007:ユーザIDのみを最大桁 + 1でログイン
login_form = LoginForm({
  "user_id": "12345678901234567",
  "password": "tejamvpose4jt4ebtjos",
})
LoginAuth(driver).doLogin(login_form)

# E008:パスワードのみを最大桁 + 1でログイン
login_form = LoginForm({
  "user_id": "E008",
  "password": "12345678901234567",
})
LoginAuth(driver).doLogin(login_form)

log.write("login_auth_test終了")

exit
# ここまでメイン処理
