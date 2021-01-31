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

    def getMailAddress(self):
        '''
        メールアドレスを返す
        '''
        return self.params["mailAddress"]

    def getPassword(self):
        '''
        パスワードを返す
        '''
        return self.params["password"]
