# -*- coding:utf-8 -*-
'''
Created on 2020/06/07

健康情報操作関連の処理を行う
@version: 1.0.0
'''
from time import sleep

from src.main.python.common import const_data


class HealthInfoRegist:
    '''
    健康情報作成を行うクラス
    '''

    def __init__(self, driver):
        '''
        Constructor
        @param driver Driver
        '''
        self.driver = driver

    def doRegist(self, healthinfo_regist_form):
        '''
        健康情報作成処理
        @param healthinfo_regist_form 健康情報作成Form情報
        '''

        '''
        健康情報入力画面の処理
        '''
        # 健康情報入力画面を表示する
        self.driver.get(const_data.BASE_REQUEST_URL + 'healthinfo/input')

        # 健康情報入力画面 - heightを設定
        self.driver.find_element_by_id("height").click()
        self.driver.find_element_by_id("height").clear()
        self.driver.find_element_by_id("height").send_keys(healthinfo_regist_form.getHeight())

        # 健康情報入力画面 - weightを設定
        self.driver.find_element_by_id("weight").click()
        self.driver.find_element_by_id("weight").clear()
        self.driver.find_element_by_id("weight").send_keys(healthinfo_regist_form.getWeight())

        # 読込を待つために1秒間処理を止める
        sleep(1)

        # 確認ボタン押下し、健康情報作成確認画面へ遷移
        submit_button = self.driver.find_element_by_class_name("btn-success")
        submit_button.click()

        # 読込を待つために1秒間処理を止める
        sleep(1)

        '''
        健康情報作成確認画面の処理
        '''
        # 確認ボタン押下し、健康情報作成完了画面へ遷移
        submit_button = self.driver.find_element_by_class_name("btn-success")
        submit_button.click()


class HealthInfoReference:
    '''
    健康情報照会を行うクラス
    '''

    def __init__(self, driver):
        '''
        Constructor
        @param driver Driver
        '''
        self.driver = driver

    def doReference(self, healthinfo_reference_form):
        '''
        健康情報照会処理
        @param healthinfo_reference_form 健康情報照会Form情報
        '''

        '''
        健康情報照会画面の処理
        '''
        # 健康情報照会画面を表示する
        self.driver.get(const_data.BASE_REQUEST_URL + 'healthinforeference/index')

        # 健康情報照会画面 - seqHealthInfoIdを設定
        self.driver.find_element_by_id("seqHealthInfoId").click()
        self.driver.find_element_by_id("seqHealthInfoId").clear()
        self.driver.find_element_by_id("seqHealthInfoId").send_keys(healthinfo_reference_form.getSeqHealthInfoId())

        # 健康情報照会画面 - healthInfoRegDateSelectFlagを設定
        self.driver.find_element_by_id("healthInfoRegDateSelectFlag").click()
        self.driver.find_element_by_id("healthInfoRegDateSelectFlag").clear()
        self.driver.find_element_by_id("healthInfoRegDateSelectFlag").send_keys(healthinfo_reference_form.getHealthInfoRegDateSelectFlag())

        # 健康情報照会画面 - fromHealthInfoRegDateを設定
        self.driver.find_element_by_id("fromHealthInfoRegDate").click()
        self.driver.find_element_by_id("fromHealthInfoRegDate").clear()
        self.driver.find_element_by_id("fromHealthInfoRegDate").send_keys(healthinfo_reference_form.getFromHealthInfoRegDate())

        # 健康情報照会画面 - toHealthInfoRegDateを設定
        self.driver.find_element_by_id("toHealthInfoRegDate").click()
        self.driver.find_element_by_id("toHealthInfoRegDate").clear()
        self.driver.find_element_by_id("toHealthInfoRegDate").send_keys(healthinfo_reference_form.getToHealthInfoRegDate())

        # 読込を待つために1秒間処理を止める
        sleep(1)

        # 確認ボタン押下し、健康情報照会画面へ遷移
        submit_button = self.driver.find_element_by_class_name("btn-success")
        submit_button.click()

