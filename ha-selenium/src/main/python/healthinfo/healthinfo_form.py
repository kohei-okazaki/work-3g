# -*- coding:utf-8 -*-
'''
Created on 2020/06/07

健康情報関連のForm
@version: 1.0.0
'''
from src.main.python.common.form import BaseForm


class HealthInfoRegistForm(BaseForm):
    '''
    健康情報登録画面Form
    '''

    def __init__(self, params):
        '''
        Constructor
        @params params パラメータMap
        '''
        self.params = params;

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


class HealthInfoReferenceForm(BaseForm):
    '''
    健康情報登録画面Form
    '''

    def __init__(self, params):
        '''
        Constructor
        @params params パラメータMap
        '''
        self.params = params;

    def getSeqHealthInfoId(self):
        '''
        健康情報IDを返す
        '''
        return self.params["seqHealthInfoId"]

    def getHealthInfoRegDateSelectFlag(self):
        '''
        健康情報作成日直接指定フラグを返す
        '''
        return self.params["healthInfoRegDateSelectFlag"]

    def getFromHealthInfoRegDate(self):
        '''
        健康情報作成日(開始)を返す
        '''
        return self.params["fromHealthInfoRegDate"]

    def getToHealthInfoRegDate(self):
        '''
        健康情報作成日(終了)を返す
        '''
        return self.params["toHealthInfoRegDate"]
