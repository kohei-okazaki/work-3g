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
    '''
    LoginAuth(driver).doLogin(LoginForm({
        "user_id": "seleniumuser",
        "password": "seleniumuser",
    }))
