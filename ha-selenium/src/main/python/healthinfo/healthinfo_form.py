'''
Created on 2020/06/07

健康情報関連のForm
@version: 1.0.0
'''


class HealthInfoRegistForm:
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
