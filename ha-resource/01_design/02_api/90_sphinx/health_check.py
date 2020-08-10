# -*- coding: utf-8 -*-
class HealthCheckAPI:
    """
    ヘルスチェックAPI
    """

    def health_check(self):
        """
        健康管理APIサーバのヘルスチェックを行う

        Url:
            http://localhost:8081/api/healthcheck

        Method:
            GET

        Header:
            ==============  ================
            Key             Value
            ==============  ================
            Accept-Charset  utf-8
            ==============  ================

        Returns:
            string: ヘルスチェックAPI 応答JSON

        """
        return {
            "result": "0",
            "error": null,
            "account":null
        }
