# -*- coding:utf-8 -*-
'''
Created on 2020/06/14

カロリー計算のテストクラス
@version: 1.0.0
'''
from os import path
from time import sleep

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
# N001:10歳、男性の入力情報で計算
calorie_form = CalorieForm({
    "age":"10",
    "gender":"0",
    "height":"165.00",
    "weight":"55.30",
    "lifeWorkMetabolism":"1000",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# N002:10歳、女性の入力情報で計算
calorie_form = CalorieForm({
    "age":"10",
    "gender":"1",
    "height":"165.00",
    "weight":"55.30",
    "lifeWorkMetabolism":"1000",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# # 異常系
# E001:年齢のみ未指定
calorie_form = CalorieForm({
    "age":"",
    "gender":"1",
    "height":"165.00",
    "weight":"55.30",
    "lifeWorkMetabolism":"1000",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# E002:性別のみ未指定
calorie_form = CalorieForm({
    "age":"10",
    "gender":"",
    "height":"165.00",
    "weight":"55.30",
    "lifeWorkMetabolism":"1000",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# E003:身長のみ未指定
calorie_form = CalorieForm({
    "age":"10",
    "gender":"0",
    "height":"",
    "weight":"55.30",
    "lifeWorkMetabolism":"1000",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# E004:体重のみ未指定
calorie_form = CalorieForm({
    "age":"10",
    "gender":"0",
    "height":"165.0",
    "weight":"",
    "lifeWorkMetabolism":"1000",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# E005:生活活動代謝のみ未指定
calorie_form = CalorieForm({
    "age":"10",
    "gender":"0",
    "height":"165.0",
    "weight":"65.0",
    "lifeWorkMetabolism":"",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# E006:年齢に数字以外を指定
calorie_form = CalorieForm({
    "age":"test",
    "gender":"0",
    "height":"165.0",
    "weight":"65.0",
    "lifeWorkMetabolism":"100",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# E007:性別に数字以外を指定
calorie_form = CalorieForm({
    "age":"10",
    "gender":"test",
    "height":"165.0",
    "weight":"65.0",
    "lifeWorkMetabolism":"100",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# E008:身長に数字以外を指定
calorie_form = CalorieForm({
    "age":"10",
    "gender":"0",
    "height":"test",
    "weight":"65.0",
    "lifeWorkMetabolism":"100",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# E009:体重に数字以外を指定
calorie_form = CalorieForm({
    "age":"10",
    "gender":"0",
    "height":"165.0",
    "weight":"test",
    "lifeWorkMetabolism":"100",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

# E010:生活活動代謝に数字以外を指定
calorie_form = CalorieForm({
    "age":"10",
    "gender":"0",
    "height":"165.0",
    "weight":"65.0",
    "lifeWorkMetabolism":"test",
})
Calorie(driver).doCalc(calorie_form)

# 目視のため1秒止める
sleep(1)

log.write(str(path.basename(__file__)) + "終了")

exit
# ここまでメイン処理
