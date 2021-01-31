# -*- coding:utf-8 -*-
'''
Created on 2021/01/31

肺活量計算のテストクラス
@version: 1.0.0
'''
from os import path
from time import sleep

from src.main.python.breathingcapacity.breathingcapacity import BreathingCapacity
from src.main.python.breathingcapacity.breathingcapacity_form import BreathingCapacityForm
from src.main.python.common.logger import Logger
from src.main.python.common.selenium_driver import SeleniumDriver
from src.main.python.common.util import login_default_selenium_user

# ここからメイン処理
log = Logger("selenium.log")
log.write(str(path.basename(__file__)) + "開始")

# driverを取得
driver = SeleniumDriver().getDriver()

# ログイン処理を先に行う
login_default_selenium_user(driver)

# # 正常系
# N001:10歳、男性の入力情報で計算
form = BreathingCapacityForm({
    "age":"10",
    "gender":"0",
    "height":"165.00",
})
BreathingCapacity(driver).doCalc(form)

# 目視のため1秒止める
sleep(1)

# N002:10歳、女性の入力情報で計算
form = BreathingCapacityForm({
    "age":"10",
    "gender":"1",
    "height":"165.00",
})
BreathingCapacity(driver).doCalc(form)

# 目視のため1秒止める
sleep(1)

# # 異常系
# E001:年齢のみ未指定
form = BreathingCapacityForm({
    "age":"",
    "gender":"0",
    "height":"165.00",
})
BreathingCapacity(driver).doCalc(form)

# 目視のため1秒止める
sleep(1)

# E002:性別のみ未指定
form = BreathingCapacityForm({
    "age":"10",
    "gender":"",
    "height":"165.00",
})
BreathingCapacity(driver).doCalc(form)

# 目視のため1秒止める
sleep(1)

# E003:身長のみ未指定
form = BreathingCapacityForm({
    "age":"10",
    "gender":"0",
    "height":"",
})
BreathingCapacity(driver).doCalc(form)

# 目視のため1秒止める
sleep(1)

# E004:年齢に数字以外を指定
form = BreathingCapacityForm({
    "age":"test",
    "gender":"0",
    "height":"165.00",
})
BreathingCapacity(driver).doCalc(form)

# 目視のため1秒止める
sleep(1)

# E004:性別に数字以外を指定
form = BreathingCapacityForm({
    "age":"10",
    "gender":"test",
    "height":"165.00",
})
BreathingCapacity(driver).doCalc(form)

# 目視のため1秒止める
sleep(1)

# E005:身長に数字以外を指定
form = BreathingCapacityForm({
    "age":"10",
    "gender":"0",
    "height":"test",
})
BreathingCapacity(driver).doCalc(form)

# 目視のため1秒止める
sleep(1)

