# -*- coding: utf-8 -*-
class HealthInfoRegistAPI:
    """
    健康情報登録API
    """

    def regist(self):
        """
        ユーザの健康情報を登録する

        Url:
            http://localhost:8081/api/${user_id}/healthinfo

        Method:
            POST

        Header:
            ==============  ==================
            Key             Value
            ==============  ==================
            Content-Type    application/json  
            Accept-Charset  utf-8             
            Api-Key         APIKEY            
            ==============  ==================

        Request_Body:
            json (string): 健康情報登録API 要求JSON

            ==============  ================  ==========================================
            Key             Type              Detail
            ==============  ================  ==========================================
            height          double            ユーザの身長
            weight          double            ユーザの体重
            testMode        string            非推奨、試験モード
            ==============  ================  ==========================================

        Response_Body:
            json (string): 健康情報登録API 応答JSON

            ==============  ================  ================  ==========================================
            Key                               Type              Detail
            ==============  ================  ================  ==========================================
            result                            string            API実行処理結果
            error                             object            status = 0の時、Null
            error           code              string            エラーコード
            error           detail            string            エラー詳細情報
            account                           object            status = 1の時、Null
            account         userId            string            ユーザを一意に識別するID
            healthInfo                        object            status = 1の時、Null
            healthInfo      seqHealthInfoId   int               健康情報一意に識別するID
            healthInfo      height            double            ユーザの身長
            healthInfo      weight            double            ユーザの体重
            healthInfo      bmi               double            ユーザのBMI
            healthInfo      standardWeight    double            ユーザの標準体重
            healthInfo      status            string            前回登録したときとの体重差のステータス
            healthInfo      regDate           string            yyyyMMddHHmmss
            ==============  ================  ================  ==========================================

        """

        return {
            "result": "0",
            "error": null,
            "account": {
                "userId": "master"
             },
            "healthInfo": {
                "seqHealthInfoId": 140,
                "height": 160.12,
                "weight": 50.12,
                "bmi": 20.123,
                "standardWeight": 50.99,
                "status": "10",
                "regDate": "20200810104404"
            }
        }
