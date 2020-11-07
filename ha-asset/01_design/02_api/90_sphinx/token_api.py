# -*- coding: utf-8 -*-
class TokenAPI:
    """
    Token発行API
    """

    def tokenApi(self):
        """
        NodeAPIで共通的に事前に呼ぶToken発行を行う

        Url:
            http://localhost:3000/token

        Method:
            POST

        Header:
            ==============  ================
            Key             Value
            ==============  ================
            Content-Type    application/json
            ==============  ================

        Request_Body:
            json (string): Token発行API 要求JSON

            ==============  ================  ==========================================
            Key             Type              Detail
            ==============  ================  ==========================================
            userId          string            ユーザID
            ==============  ================  ==========================================

        Response_Body:
            json (string): Token発行API 応答JSON

            ==================  ================  =============================================
            Key                 Type              Detail
            ==================  ================  =============================================
            result              int               API実行処理結果
            detail              string            エラー事由
            token               string            Nodeが発行したAPI用トークン(期限は1時間とする)
            ==================  ================  =============================================

        """
        return {
          "result": 0,
          "token": "fjmwavjw98tawp0tbanwpthanp0mfjvn"
        }
