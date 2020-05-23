# -*- coding:utf-8 -*-
'''
Created on 2020/05/23

ログイン処理をするPython
@version: 1.0.0
'''
from time import sleep
from src.main.python.common import const_data


class LoginAuth:
    '''
    ログイン認証処理を行うクラス
    '''

    def __init__(self, driver, userId, password):
        '''
        Constructor
        @param driver Driver
        @param userId ログインID
        @param password パスワード
        '''
        self.driver = driver
        self.userId = userId
        self.password = password

    def doLogin(self):
        '''
        ログイン処理
        '''

        # ログイン画面を表示する
        self.driver.get(const_data.BASE_REQUEST_URL + '/login/index')

        # ログイン画面 - loginIdを設定
        loginIdElement = self.driver.find_element_by_id("userId")
        loginIdElement.send_keys(self.userId)

        # ログイン画面 - passwordを設定
        passwordElement = self.driver.find_element_by_id("password")
        passwordElement.send_keys(self.password)

        # 読み込みを待つために2秒間処理を止める
        sleep(1)

        # ログインボタン押下し、TOP画面へ遷移
        submitButton = self.driver.find_element_by_id("submit")
        submitButton.click()

        # 読み込みを待つために2秒間処理を止める
        sleep(1)
