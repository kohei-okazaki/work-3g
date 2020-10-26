# -*- coding:utf-8 -*-
'''
Created on 2020/10/26

肺活量計算を行う
@version: 1.0.0
'''
from time import sleep

from src.main.python.common import const_data


class BreathingCapacity:
    '''
    肺活量計算を行うクラス
    '''

    def __init__(self, driver):
        '''
        Constructor
        @param driver Driver
        '''
        self.driver = driver

    def doCalc(self, breathingcapacity_form, isMale):
        '''
        肺活量計算を行う
        @param breathingcapacity_form 肺活量計算画面のForm
        @param isMale 男性の場合True、それ以外の場合False
        '''

        '''
        肺活量計算画面の入力情報を設定
        '''
        #  肺活量計算入力画面を表示する
        self.driver.get(const_data.BASE_REQUEST_URL + 'breathingcapacity/index')

        #  肺活量計算入力画面 - 年齢を設定
        self.driver.find_element_by_id("age").click()
        self.driver.find_element_by_id("age").clear()
        self.driver.find_element_by_id("age").send_keys(breathingcapacity_form.getAge())

        #  肺活量計算入力画面 - 性別を設定
        if isMale:
            self.driver.find_element_by_id("male").send_keys(breathingcapacity_form.getGender())
        else :
            self.driver.find_element_by_id("female").send_keys(breathingcapacity_form.getGender())

        # 肺活量計算入力画面 - 身長を設定
        self.driver.find_element_by_id("height").click()
        self.driver.find_element_by_id("height").clear()
        self.driver.find_element_by_id("height").send_keys(breathingcapacity_form.getHeight())

        # 読込を待つために2秒間処理を止める
        sleep(2)

        # 確認ボタン押下し、肺活量計算処理を行い計算結果画面を表示
        submit_button = self.driver.find_element_by_xpath(u"//input[@value='計 算']")
        submit_button.click()
