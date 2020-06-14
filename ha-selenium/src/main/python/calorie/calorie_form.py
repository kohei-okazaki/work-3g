# -*- coding:utf-8 -*-
'''
Created on 2020/06/14

カロリー関連のForm
@version: 1.0.0
'''
from src.main.python.common.form import BaseForm


class CalorieForm(BaseForm):
    '''
    カロリー計算用のFormクラス
    '''

    def __init__(self, params):
        '''
        Constructor
        @params params パラメータMap
        '''
        self.params = params;

    def getAge(self):
        '''
        年齢を返す
        '''
        return self.params["age"]

    def getGender(self):
        '''
        性別を返す
        '''
        return self.params["gender"]

    def getHeight(self):
        '''
        身長を返す
        '''
        return self.params["height"]

    def getWeight(self):
        '''
        体重を返す
        '''
        return self.params["weight"]

    def getLifeWorkMetabolism(self):
        '''
        生活活動代謝を返す
        '''
        return self.params["lifeWorkMetabolism"]
