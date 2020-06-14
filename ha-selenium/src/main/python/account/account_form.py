# -*- coding:utf-8 -*-
'''
Created on 2020/06/07

アカウント関連のForm
@version: 1.0.0
'''
from src.main.python.common.form import BaseForm


class BaseAccountForm(BaseForm):
    '''
    アカウントの親Formクラス
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


class AccountRegistForm(BaseAccountForm):
    '''
    アカウント作成用のFormクラス
    '''

    def __init__(self, params):
        '''
        Constructor
        @params params パラメータMap
        '''
        self.params = params;

    def getConfirmPassword(self):
        '''
        確認用パスワードを返す
        '''
        return self.params["confirmPassword"]

    def getRemarks(self):
        '''
        備考を返す
        '''
        return self.params["remarks"]


class AccountSettingForm(BaseAccountForm):
    '''
    アカウント設定変更用のFormクラス
    '''

    def __init__(self, params):
        '''
        Constructor
        @params params パラメータMap
        '''
        self.params = params;

    def getDeleteFlag(self):
        '''
        パスワードを返す
        '''
        return self.params["deleteFlag"]

    def getRemarks(self):
        '''
        備考を返す
        '''
        return self.params["remarks"]

    def getMailAddress(self):
        '''
        メールアドレスを返す
        '''
        return self.params["mailAddress"]

    def getMailPassword(self):
        '''
        メールパスワードを返す
        '''
        return self.params["mailPassword"]

    def getPasswordExpire(self):
        '''
        パスワード有効期限を返す
        '''
        return self.params["passwordExpire"]

    def getHeaderFlag(self):
        '''
        ヘッダ利用有無フラグを返す
        '''
        return self.params["headerFlag"]

    def getFooterFlag(self):
        '''
        フッタ利用有無フラグを返す
        '''
        return self.params["footerFlag"]

    def getMaskFlag(self):
        '''
        マスク利用有無フラグを返す
        '''
        return self.params["maskFlag"]

    def getEnclosureCharFlag(self):
        '''
        囲み文字利用有無フラグを返す
        '''
        return self.params["enclosureCharFlag"]
