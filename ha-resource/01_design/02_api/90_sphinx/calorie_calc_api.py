# -*- coding: utf-8 -*-
class CalorieCalcAPI:
    """
    カロリー計算API
    """

    def calc(self):
        """
        ユーザのカロリーを計算する

        Url:
            http://localhost:3000/calorie

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
            json (string): カロリー計算API 要求JSON

            ======================  ================  =======================
            Key                     Type              Detail
            ======================  ================  =======================
            gender                  string            ユーザの性別
            age                     int               ユーザの年齢
            height                  double            ユーザの身長
            weight                  double            ユーザの体重
            life_work_metabolism    double            ユーザの生活活動代謝
            ======================  ================  =======================

        Response_Body:
            json (string): 基礎健康情報計算API 応答JSON

            ====================  ======================  ================  ==========================================
            Key                                           Type              Detail
            ====================  ======================  ================  ==========================================
            result                                        int               API実行処理結果
            detail                                        string            エラー事由
            calorie_calc_result                           object            status = 1の時、Null
            calorie_calc_result   base_metabolism         double            ユーザの身長
            calorie_calc_result   lost_calorie_per_day    double            ユーザの体重
            user_data                                     object            status = 1の時、Null
            user_data             gender                  double            ユーザの性別
            user_data             age                     double            ユーザの年齢
            user_data             height                  double            ユーザの身長
            user_data             weight                  double            ユーザの体重
            user_data             life_work_metabolism    double            ユーザの生活活動代謝
            ====================  ======================  ================  ==========================================

        """
        return {
            "result": 0,
            "calorie_calc_result": {
              "base_metabolism": 2460.058,
              "lost_calorie_per_day": 2583.514
            },
            "user_data": {
              "gender": "1",
              "age": 25,
              "height": 476.68,
              "weight": 69.64,
              "life_work_metabolism": 123.456
            }
          }
