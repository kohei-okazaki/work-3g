# -*- coding:utf-8 -*-
'''
Created on 2020/06/14

カロリー計算を行う
@version: 1.0.0
'''
from time import sleep

from src.main.python.common import const_data


class Calorie(object):
    '''
    カロリー計算処理を行うクラス
    '''

    def __init__(self, driver):
        '''
        Constructor
        @param driver Driver
        '''
        self.driver = driver

    def doCalc(self, calorie_form):
        '''
        カロリー計算を行う
        @param calorie_form カロリー計算画面のForm
        '''

        '''
        カロリー計算入力画面の処理
        '''
        # カロリー計算入力画面を表示する
        self.driver.get(const_data.BASE_REQUEST_URL + 'caloriecalc/index')

        # カロリー計算入力画面 - 年齢を設定
        self.driver.find_element_by_id("age").click()
        self.driver.find_element_by_id("age").clear()
        self.driver.find_element_by_id("age").send_keys(calorie_form.getAge())

        #  肺活量計算入力画面 - 性別を設定
        if calorie_form.getGender == "0":
            self.driver.find_element_by_id("male").send_keys(calorie_form.getGender())
        else :
            self.driver.find_element_by_id("female").send_keys(calorie_form.getGender())

        # カロリー計算入力画面 - 身長を設定
        self.driver.find_element_by_id("height").click()
        self.driver.find_element_by_id("height").clear()
        self.driver.find_element_by_id("height").send_keys(calorie_form.getHeight())

        # カロリー計算入力画面 - 体重を設定
        self.driver.find_element_by_id("weight").click()
        self.driver.find_element_by_id("weight").clear()
        self.driver.find_element_by_id("weight").send_keys(calorie_form.getWeight())

        # カロリー計算入力画面 - 生活活動代謝を設定
        self.driver.find_element_by_id("lifeWorkMetabolism").click()
        self.driver.find_element_by_id("lifeWorkMetabolism").clear()
        self.driver.find_element_by_id("lifeWorkMetabolism").send_keys(calorie_form.getLifeWorkMetabolism())

        # 読込を待つために2秒間処理を止める
        sleep(2)

        # 確認ボタン押下し、カロリー計算処理を行い計算結果画面を表示
        submit_button = self.driver.find_element_by_xpath(u"//input[@value='計 算']")
        submit_button.click()
