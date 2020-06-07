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
# E001:既に登録したユーザIDで登録処理を行う(N001のデータを利用)
account_regist_form = AccountRegistForm({
  "user_id": "testID001",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "ここが備考欄",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E002:ユーザIDのみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E003:パスワードのみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "testID005",
  "password": "",
  "confirmPassword": "hoge123456",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E004:確認用パスワードのみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "testID005",
  "password": "hoge123456",
  "confirmPassword": "",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E005:ユーザ1桁のみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "a",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E006:パスワード1桁のみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "testID005",
  "password": "a",
  "confirmPassword": "hoge123456",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E007:確認用パスワード1桁のみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "testID005",
  "password": "hoge123456",
  "confirmPassword": "a",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E008:ユーザIDを最大 + 1のみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "12345678901234567",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E009:パスワードを最大 + 1のみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "testID005",
  "password": "12345678901234567",
  "confirmPassword": "hoge123456",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E010:確認用パスワードを最大 + 1のみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "testID005",
  "password": "hoge123456",
  "confirmPassword": "12345678901234567",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E011:パスワードと確認用パスワードを最大 + 1のみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "testID005",
  "password": "12345678901234567",
  "confirmPassword": "12345678901234567",
  "remarks": "",
})
AccountRegist(driver).doRegst(account_regist_form, True)

# E012:備考を最大 + 1のみ未入力
account_regist_form = AccountRegistForm({
  "user_id": "testID005",
  "password": "hoge123456",
  "confirmPassword": "hoge123456",
  "remarks": "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567",
})
AccountRegist(driver).doRegst(account_regist_form, True)

log.write("login_auth_test終了")

exit
# ここまでメイン処理
