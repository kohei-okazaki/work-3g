# -*- coding:utf-8 -*-
'''
Created on 2020/05/23

ログインFormのPython
@version: 1.0.0
'''


class LoginForm:
    '''
    ログインForm
    '''

    def __init__(self, params):
        '''
        Constructor
        '''
        self.params = params;

    def getUserId(self):
        '''
        ユーザIDを返す
        '''
        return self.params["userId"]

    def getPassword(self):
        '''
        パスワードを返す
        '''
        return self.params["password"]
