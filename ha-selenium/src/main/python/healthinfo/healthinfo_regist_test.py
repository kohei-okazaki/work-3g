# -*- coding:utf-8 -*-
'''
Created on 2020/06/07

健康情報作成処理をテストするPython
@version: 1.0.0
'''

from os import path

from src.main.python.common.logger import Logger
from src.main.python.common.selenium_driver import SeleniumDriver
from src.main.python.common.util import login_default_selenium_user
from src.main.python.healthinfo.healthinfo import HealthInfoRegist
from src.main.python.healthinfo.healthinfo_form import HealthInfoRegistForm

# ここからメイン処理
log = Logger("selenium.log")
log.write(str(path.basename(__file__)) + "開始")

# driverを取得
driver = SeleniumDriver().getDriver()

# ログイン処理を先に行う
login_default_selenium_user(driver)

# # 正常系
# N001:適当な健康情報を登録
healthinfo_regist_form = HealthInfoRegistForm({
  "height": "170.10",
  "weight": "65.00",
})
HealthInfoRegist(driver).doRegist(healthinfo_regist_form)

# # 異常系
# E001:身長を未入力
healthinfo_regist_form = HealthInfoRegistForm({
  "height": "",
  "weight": "65.00",
})
HealthInfoRegist(driver).doRegist(healthinfo_regist_form)

# E002:体重を未入力
healthinfo_regist_form = HealthInfoRegistForm({
  "height": "170.10",
  "weight": "",
})
HealthInfoRegist(driver).doRegist(healthinfo_regist_form)

# E003:身長に半角数字以外を入力
healthinfo_regist_form = HealthInfoRegistForm({
  "height": "test",
  "weight": "65.00",
})
HealthInfoRegist(driver).doRegist(healthinfo_regist_form)

# E004:体重に半角数字以外を入力
healthinfo_regist_form = HealthInfoRegistForm({
  "height": "170.10",
  "weight": "test",
})
HealthInfoRegist(driver).doRegist(healthinfo_regist_form)

log.write(str(path.basename(__file__)) + "終了")

exit
# ここまでメイン処理
