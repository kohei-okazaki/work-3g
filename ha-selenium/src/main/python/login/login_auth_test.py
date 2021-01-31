# -*- coding:utf-8 -*-
'''
Created on 2020/05/23

ログイン処理をテストするPython
@version: 1.0.0
'''

from os import path

from src.main.python.common.logger import Logger
from src.main.python.common.selenium_driver import SeleniumDriver
from src.main.python.login.login_auth import LoginAuth
from src.main.python.login.login_form import LoginForm

# ここからメイン処理
log = Logger("selenium.log")
log.write(str(path.basename(__file__)) + "開始")

# driverを取得
driver = SeleniumDriver().getDriver()

# N001:正しいログイン情報でログイン
login_form = LoginForm({
  "mailAddress": "selenium@gmail.com",
  "password": "seleniumuser",
})
LoginAuth(driver).doLogin(login_form)

# ブラウザバック
driver.back()

# N002:正しいログイン情報でログイン
login_form = LoginForm({
  "mailAddress": "selenium@gmail.com",
  "password": "seleniumuser",
})
LoginAuth(driver).doLogin(login_form)

# ログアウトボタン押下
LoginAuth(driver).doLogout()

# E001:正しくないパスワードでログイン
login_form = LoginForm({
  "mailAddress": "selenium@gmail.com",
  "password": "hoge",
})
LoginAuth(driver).doLogin(login_form)

# E002:存在しないユーザIDでログイン
login_form = LoginForm({
  "mailAddress": "selenium_no_user@gmail.com",
  "password": "seleniumuser",
})
LoginAuth(driver).doLogin(login_form)

# 入力チェック
# E003:メールアドレスのみを未入力でログイン
login_form = LoginForm({
  "mailAddress": "",
  "password": "seleniumuser",
})
LoginAuth(driver).doLogin(login_form)

# E004:パスワードのみを未入力でログイン
login_form = LoginForm({
    "mailAddress": "selenium@gmail.com",
    "password": "",
})
LoginAuth(driver).doLogin(login_form)

# E005:パスワードのみを最小桁 - 1桁でログイン
login_form = LoginForm({
  "mailAddress": "selenium@gmail.com",
  "password": "1",
})
LoginAuth(driver).doLogin(login_form)

# E007:ユーザIDのみを最大桁 + 1でログイン
login_form = LoginForm({
  "mailAddress": "12345678901234567890123456789012345678901234567890123456789012345678901234",
  "password": "seleniumuser",
})
LoginAuth(driver).doLogin(login_form)

# E008:パスワードのみを最大桁 + 1でログイン
login_form = LoginForm({
    "mailAddress": "selenium@gmail.com",
    "password": "12345678901234567890123456789012345678901234567890123456789012345678901234",
})
LoginAuth(driver).doLogin(login_form)

log.write(str(path.basename(__file__)) + "終了")

exit
# ここまでメイン処理
