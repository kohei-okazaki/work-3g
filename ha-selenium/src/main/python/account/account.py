'''
Created on 2020/06/07
アカウント関連のPython
@version: 1.0.0
'''
from time import sleep

from src.main.python.common import const_data


class AccountRegist(object):
    '''
    アカウント作成を行うクラス
    '''

    def __init__(self, driver):
        '''
        Constructor
        @param driver Driver
        '''
        self.driver = driver

    def doRegst(self, account_regist_form, is_return_login):
        '''
        アカウント作成処理
        @param account_regist_form アカウント作成Form情報
        @param is_return_login ログイン画面に戻るかどうかのフラグ(戻る場合、Trueを指定)
        '''

        '''
        アカウント作成画面の処理
        '''
        # アカウント作成画面を表示する
        self.driver.get(const_data.BASE_REQUEST_URL + 'accountregist/input')

        # アカウント作成画面 - userIdを設定
        user_id_element = self.driver.find_element_by_id("userId")
        user_id_element.send_keys(account_regist_form.getUserId())

        # アカウント作成画面 - passwordを設定
        password_element = self.driver.find_element_by_id("password")
        password_element.send_keys(account_regist_form.getPassword())

        # アカウント作成画面 - confirmPasswordを設定
        confirm_password_element = self.driver.find_element_by_id("confirmPassword")
        confirm_password_element.send_keys(account_regist_form.getConfirmPassword())

        # アカウント作成画面 - remarksを設定
        remarks_element = self.driver.find_element_by_id("remarks")
        remarks_element.send_keys(account_regist_form.getRemarks())

        # 読込を待つために1秒間処理を止める
        sleep(1)

        # 確認ボタン押下し、アカウント作成確認画面へ遷移
        submit_button = self.driver.find_element_by_class_name("btn-success")
        submit_button.click()

        # 読込を待つために1秒間処理を止める
        sleep(1)

        '''
        アカウント作成確認画面の処理
        '''
        # 確認ボタン押下し、アカウント作成完了画面へ遷移
        submit_button = self.driver.find_element_by_class_name("btn-success")
        submit_button.click()

        '''
        アカウント作成完了画面の処理
        '''
        if is_return_login:
            # ログイン画面に戻る場合、送信ボタンのタグを取得し押下
            submit_button = self.driver.find_element_by_class_name("btn-success")
            submit_button.click()


class AccountSetting:
    '''
    アカウント設定を行うクラス
    '''

    def __init__(self, driver):
        '''
        Constructor
        @param driver Driver
        '''
        self.driver = driver
