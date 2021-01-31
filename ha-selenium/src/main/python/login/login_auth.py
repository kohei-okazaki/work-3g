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

    def __init__(self, driver):
        '''
        Constructor
        @param driver Driver
        '''
        self.driver = driver

    def doLogin(self, login_form):
        '''
        ログイン処理
        @param login_form ログインForm情報
        '''

        # ログイン画面を表示する
        self.driver.get(const_data.BASE_REQUEST_URL + 'login/index')

        # ログイン画面 - mailAddressを設定
        self.driver.find_element_by_id("mailAddress").click()
        self.driver.find_element_by_id("mailAddress").clear()
        self.driver.find_element_by_id("mailAddress").send_keys(login_form.getMailAddress())

        # ログイン画面 - passwordを設定
        self.driver.find_element_by_id("password").click()
        self.driver.find_element_by_id("password").clear()
        self.driver.find_element_by_id("password").send_keys(login_form.getPassword())

        # 読込を待つために1秒間処理を止める
        sleep(1)

        # ログインボタン押下し、TOP画面へ遷移
        submit_button = self.driver.find_element_by_id("submit")
        submit_button.click()

        # 読込を待つために1秒間処理を止める
        sleep(1)

    def doLogout(self):
        '''
        ログアウト処理
        '''

        # ログアウトのリンクを選択
        logout_link_element = self.driver.find_element_by_link_text("ログアウト")

        logout_link_element.click()
