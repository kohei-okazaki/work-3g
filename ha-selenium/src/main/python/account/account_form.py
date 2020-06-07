'''
Created on 2020/06/07

アカウント関連のForm
@version: 1.0.0
'''


class AccountRegistForm(object):
    '''
    アカウント作成用のFormクラス
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
        return self.params["user_id"]

    def getPassword(self):
        '''
        パスワードを返す
        '''
        return self.params["password"]

    def getConfirmPassword(self):
        '''
        パスワードを返す
        '''
        return self.params["confirmPassword"]

    def getRemarks(self):
        '''
        パスワードを返す
        '''
        return self.params["remarks"]
