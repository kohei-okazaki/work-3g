# -*- coding:utf-8 -*-
'''
Created on 2020/06/09

健康管理アプリで使用する共通的な関数をまとめたPython
@version: 1.0.0
'''
from src.main.python.login.login_auth import LoginAuth
from src.main.python.login.login_form import LoginForm


def login_default_selenium_user(driver):
    '''
    健康情報画面がログイン後の画面のため、最初にログイン処理を行う
    @driver Driver
    '''
    LoginAuth(driver).doLogin(LoginForm({
        "mailAddress": "selenium@gmail.com",
        "password": "seleniumuser",
    }))


def setTextById(driver, element_id, text):
    '''
    指定したブラウザに文字列を設定する
    @param element_id ブラウザ情報のDOMのキー
    @param text 設定対象文字列
    '''
    driver.find_element_by_id(element_id).click()
    driver.find_element_by_id(element_id).clear()
    driver.find_element_by_id(element_id).send_keys(text)
