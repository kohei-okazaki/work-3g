'''
Created on 2020/06/07

アカウント作成処理をテストするPython
@version: 1.0.0
'''
from src.main.python.account.account import AccountRegist
from src.main.python.account.account_form import AccountRegistForm
from src.main.python.common.logger import Logger
from src.main.python.common.selenium_driver import SeleniumDriver

# ここからメイン処理
log = Logger("selenium.log")
log.write("account_regist_test開始")

# driverを取得
driver = SeleniumDriver().getDriver()

# # 正常系
# N001:任意のアカウントを作成(備考設定あり かつ ログイン画面に戻る)
account_regist_form = AccountRegistForm({
  "user_id": "testID001",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "ここが備考欄",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# N002:任意のアカウントを作成(備考設定あり かつ ログイン画面に戻らない)
account_regist_form = AccountRegistForm({
  "user_id": "testID002",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "ここが備考欄",
})
AccountRegist(driver).doRegst(account_regist_form, False)

# N003:任意のアカウントを作成(備考設定なし かつ ログイン画面に戻る)
account_regist_form = AccountRegistForm({
  "user_id": "testID003",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# N004:任意のアカウントを作成(備考設定なし かつ ログイン画面に戻らない)
account_regist_form = AccountRegistForm({
  "user_id": "testID004",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, False)

# # 異常系
# 既に登録したユーザIDで登録処理を行う(N001のデータを利用)
account_regist_form = AccountRegistForm({
  "user_id": "testID001",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "ここが備考欄",
})
AccountRegist(driver).doRegst(account_regist_form, True)

log.write("login_auth_test終了")

exit
# ここまでメイン処理
