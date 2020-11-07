# -*- coding: utf-8 -*-
class BreathingCapacityAPI:
    """
    肺活量計算API
    """

    def breathing_capacity(self):
        """
        肺活量を計算する

        Url:
            http://localhost:3000/breathing_capacity

        Method:
            POST

        Header:
            ==============  ====================
            Key             Value
            ==============  ====================
            Content-Type    application/json
            X-NODE-TOKEN    ${発行されたトークン}
            ==============  ====================

        Request_Body:
            json (string): 肺活量計算API 要求JSON

            ==============  ================  ==========================================
            Key             Type              Detail
            ==============  ================  ==========================================
            age             int               ユーザの年齢
            gender          string            ユーザの性別
            height          double            ユーザの身長
            ==============  ================  ==========================================

        Response_Body:
            json (string): 肺活量計算API 応答JSON

            ==============================  ================================  ================  ==========================================
            Key                                                               Type              Detail
            ==============================  ================================  ================  ==========================================
            result                                                            int               API実行処理結果
            detail                                                            string            エラー事由
            breathing_capacity_calc_result                                    object            status = 1の時、Null
            breathing_capacity_calc_result  predict_breathing_capacity        double            ユーザの予測肺活量
            breathing_capacity_calc_result  breathing_capacity_percentage     double            肺活量%
            user_data                                                         object            status = 1の時、Null
            user_data                       age                               int               ユーザの年齢
            user_data                       gender                            string            ユーザの性別
            user_data                       height                            double            ユーザの身長
            ==============================  ================================  ================  ==========================================



        """
        return {
            "result": 0,
            "breathing_capacity_calc_result": {
                "predict_breathing_capacity": 170.12,
                "breathing_capacity_percentage": 80.00
            },
            "user_data": {
                "age": 20,
                "gender": "1",
                "height": 170.12
            }
        }
