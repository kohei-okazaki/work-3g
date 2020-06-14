# -*- coding:utf-8 -*-
'''
Created on 2020/06/14

すべてのFormの親Formクラス
@version: 1.0.0
'''


class BaseForm:
    '''
    基底Formクラス
    '''

    def __init__(self, params):
        '''
        Constructor
        @params params パラメータMap
        '''
        self.params = params;

    def initialize_form(self):
        print("form create...")
