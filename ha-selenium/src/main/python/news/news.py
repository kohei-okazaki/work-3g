# -*- coding:utf-8 -*-
'''
Created on 2021/01/31

お知らせ情報操作関連の処理を行う
@version: 1.0.0
'''
from time import sleep

from src.main.python.common import const_data


class News:
    '''
    お知らせ情報操作を行うクラス
    '''

    def __init__(self, driver):
        '''
        Constructor
        @param driver Driver
        '''
        self.driver = driver

    def doGet(self):
        '''
        お知らせ情報を取得する
        '''

        # 健康情報入力画面を表示する
        self.driver.get(const_data.BASE_REQUEST_URL + 'news/list')

        # 読込を待つために1秒間処理を止める
        sleep(1)
