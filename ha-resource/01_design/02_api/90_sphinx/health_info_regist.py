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
            ==============  ================
            Key             Value
            ==============  ================
            Content-Type    application/json
            Accept-Charset  utf-8
            Api-Key         APIKEY
            ==============  ================

        Args:
            json (string): 健康情報登録API 要求JSON

        Returns:
            string: 健康情報登録API 応答JSON

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
