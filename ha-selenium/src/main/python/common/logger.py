# -*- coding:utf-8 -*-
'''
Created on 2020/05/23

LoggerのPython
@version: 1.0.0
'''
import datetime
import inspect
from src.main.python.common import const_data


class Logger:
    '''
    Loggerのクラス
    '''

    def __init__(self, filename):
        '''
        Constructor
        @param filepath ファイルパス
        '''
        self.__filepath = str(const_data.LOG_DIR) + filename

    def write(self, msg):
        '''
        書き込みメソッド
        @param msg: 出力メッセージ
        '''
        # スタックフレーム解析
        stack_frame = inspect.stack()[1]
        frame = stack_frame[0]
        info = inspect.getframeinfo(frame)

        # ログファイル内容作成
        linetxt = ""
        linetxt += datetime.datetime.now().strftime("%Y/%m/%d %H:%M:%S ")
        linetxt += (info.filename + " ")
        linetxt += (str(info.lineno) + " ")
        linetxt += msg
        linetxt += "\n"

        # ログファイルに書き込む
        with open(self.__filepath, "w", encoding='utf-8') as f:
            f.write(linetxt)
