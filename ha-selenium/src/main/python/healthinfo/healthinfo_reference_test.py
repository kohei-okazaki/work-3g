# -*- coding:utf-8 -*-
'''
Created on 2020/06/09

健康情報照会処理をテストするPython
@version: 1.0.0
'''
from os import path

from src.main.python.common.logger import Logger
from src.main.python.common.selenium_driver import SeleniumDriver
from src.main.python.common.util import login_default_selenium_user
from src.main.python.healthinfo.healthinfo import HealthInfoReference
from src.main.python.healthinfo.healthinfo_form import HealthInfoReferenceForm

# ここからメイン処理
log = Logger("selenium.log")
log.write(str(path.basename(__file__)) + "開始")

# driverを取得
driver = SeleniumDriver().getDriver()

# ログイン処理を先に行う
login_default_selenium_user(driver)

# # 正常系
# N001:健康情報IDで検索
healthinfo_reference_form = HealthInfoReferenceForm({
  "seqHealthInfoId": "75",
  "healthInfoRegDateSelectFlag": "",
  "fromHealthInfoRegDate": "",
  "toHealthInfoRegDate": "",
})
HealthInfoReference(driver).doReferenceBySeqHealthInfoId(healthinfo_reference_form)

log.write(str(path.basename(__file__)) + "終了")

exit
# ここまでメイン処理
