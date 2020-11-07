# -*- coding: utf-8 -*-
class HealthInfoRefenceAPI:
    """
    健康情報照会API
    """

    def reference(self):
        """
        ユーザの健康情報を照会する

        Url:
            http://localhost:8081/api/${user_id}/healthinfo/${seq_health_info_id}

        Method:
            GET

        Header:
            ==============  ================
            Key             Value
            ==============  ================
            Accept-Charset  utf-8
            Api-Key         APIKEY
            ==============  ================

        Response_Body:
            json (string): 健康情報照会API 応答JSON

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
                "seqHealthInfoId": 51,
                "height": 177.02,
                "weight": 65.41,
                "bmi": 20.9,
                "standardWeight": 68.86,
                "status": "10",
                "regDate": "20200418233002"
            }
        }
