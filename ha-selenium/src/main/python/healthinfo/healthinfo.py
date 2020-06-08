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
        user_id_element = self.driver.find_element_by_id("height")
        user_id_element.send_keys(healthinfo_regist_form.getHeight())

        # 健康情報入力画面 - weightを設定
        password_element = self.driver.find_element_by_id("weight")
        password_element.send_keys(healthinfo_regist_form.getWeight())

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
