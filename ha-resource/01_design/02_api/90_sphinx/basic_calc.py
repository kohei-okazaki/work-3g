# -*- coding: utf-8 -*-
class BasicCalcAPI:
    """
    基礎健康情報計算API
    """

    def calc(self):
        """
        基礎健康情報を計算する

        Url:
            http://localhost:3000/basic

        Method:
            POST

        Header:
            ==============  ================
            Key             Value
            ==============  ================
            Content-Type    application/json
            ==============  ================

        Request_Body:
            json (string): 基礎健康情報計算API 要求JSON

            ==============  ================  ==========================================
            Key             Type              Detail
            ==============  ================  ==========================================
            height          double            ユーザの身長
            weight          double            ユーザの体重
            ==============  ================  ==========================================

        Response_Body:
            json (string): 基礎健康情報計算API 応答JSON

            ==================  ================  ================  ==========================================
            Key                                   Type              Detail
            ==================  ================  ================  ==========================================
            result                                int               API実行処理結果
            detail                                string            エラー事由
            basic_health_info                     object            status = 1の時、Null
            basic_health_info   height            double            ユーザの身長
            basic_health_info   weight            double            ユーザの体重
            basic_health_info   bmi               double            ユーザのBMI
            basic_health_info   standard_weight   double            ユーザの標準体重
            ==================  ================  ================  ==========================================



        """
        return {
          "result": 0,
           "basic_health_info": {
               "height": 170.12,
               "weight": 60.12,
               "bmi": 20.123,
               "standard_weight": 60.34
             }
           }
