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
            Content-Type    application/json
            Accept-Charset  utf-8
            Api-Key         APIKEY
            ==============  ================

        Returns:
            string: 健康情報照会API 応答JSON

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
