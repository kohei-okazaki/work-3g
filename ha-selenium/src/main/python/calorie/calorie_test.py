# -*- coding:utf-8 -*-
'''
Created on 2020/06/14

カロリー計算のテストクラス
@version: 1.0.0
'''
from os import path

from src.main.python.calorie.calorie import Calorie
from src.main.python.calorie.calorie_form import CalorieForm
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
# N001:任意の入力情報で計算
calorie_form = CalorieForm({
    "age":"10",
    "gender":"0",
    "height":"165.00",
    "weight":"55.30",
    "lifeWorkMetabolism":"1000",
})
Calorie(driver).doCalc(calorie_form)

log.write(str(path.basename(__file__)) + "終了")

exit
# ここまでメイン処理
