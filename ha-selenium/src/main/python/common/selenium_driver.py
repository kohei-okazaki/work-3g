# -*- coding:utf-8 -*-
'''
Created on 2020/05/24

Selenium関連の処理をまとめたクラス
@version: 1.0.0
'''
from selenium import webdriver

from src.main.python.common import const_data


class SeleniumDriver:
    '''
    SeleniumのDriverクラス
    resources配下のchromedriver.exeが存在すること
    '''

    def __init__(self):
        '''
        Constructor
        '''

    def getDriver(self):
        return webdriver.Chrome(const_data.SELENIUM_DRIVER_PATH)
