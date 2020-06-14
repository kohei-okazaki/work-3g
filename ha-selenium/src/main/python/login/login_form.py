# -*- coding:utf-8 -*-
'''
Created on 2020/05/23

ログインFormのPython
@version: 1.0.0
'''
from src.main.python.common.form import BaseForm


class LoginForm(BaseForm):
    '''
    ログインForm
    '''

    def __init__(self, params):
        '''
        Constructor
        @params params パラメータMap
        '''
        self.params = params;

    def getUserId(self):
        '''
        ユーザIDを返す
        '''
        return self.params["user_id"]

    def getPassword(self):
        '''
        パスワードを返す
        '''
        return self.params["password"]
