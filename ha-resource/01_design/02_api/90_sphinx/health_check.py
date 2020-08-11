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

        Response_Body:
            json (string): ヘルスチェックAPI 応答JSON

            ==============  ================  ==========================================
            Key             Type              Detail
            ==============  ================  ==========================================
            result          double            API実行処理結果
            error           double            status = 0の時、Null
            account         string            Null
            ==============  ================  ==========================================

        """

        return {
            "result": "0",
            "error": null,
            "account":null
        }
