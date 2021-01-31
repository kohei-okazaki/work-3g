# -*- coding:utf-8 -*-
'''
Created on 2021/01/31

お知らせ情報画面をテストするPython
@version: 1.0.0
'''

from os import path

from src.main.python.common.logger import Logger
from src.main.python.common.selenium_driver import SeleniumDriver
from src.main.python.common.util import login_default_selenium_user
from src.main.python.news.news import News

# ここからメイン処理
log = Logger("selenium.log")
log.write(str(path.basename(__file__)) + "開始")

# driverを取得
driver = SeleniumDriver().getDriver()

# ログイン処理を先に行う
login_default_selenium_user(driver)

# お知らせ情報を取得
News(driver).doGet()
