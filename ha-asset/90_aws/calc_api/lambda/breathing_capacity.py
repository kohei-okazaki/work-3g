import json
import math


HEADERS = {
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json",
}


def _response(status_code: int, body: dict) -> dict:
    """API Gateway向けのJSONレスポンスを生成する。

    Args:
        status_code (int): HTTPステータスコード。
        body (dict): JSONに変換するレスポンス本文。

    Returns:
        dict: ステータスコード、共通ヘッダー、JSON文字列の本文を含むレスポンス。
    """
    return {
        "statusCode": status_code,
        "headers": HEADERS,
        "body": json.dumps(body, ensure_ascii=False),
    }


def _floor_3(value: float) -> float:
    """値を小数第3位まで残して負の無限大方向に丸める。

    Args:
        value (float): 丸める数値。

    Returns:
        float: 小数第4位以下を負の無限大方向に丸めた値。
    """
    return math.floor(value * 1000) / 1000


def lambda_handler(event, context):
    """クエリパラメータから予測肺活量と肺活量の割合を計算する。

    Args:
        event (dict): API Gatewayのイベント。queryStringParametersに以下を含む。
            gender: 性別（0: 男性、1: 女性）。
            age: 年齢（歳、0以上の整数）。
            height: 身長（cm、正の数）。
        context (object): Lambdaの実行コンテキスト。この関数では使用しない。

    Returns:
        dict: 成功時はHTTP 200で予測肺活量（mL）、基準肺活量の予測肺活量に
            対する割合（%）、入力値を返す。入力不備時はHTTP 400で
            エラー詳細を返す。本文はJSON文字列として格納する。
    """
    params = event.get("queryStringParameters") or {}

    try:
        gender = int(params.get("gender"))
    except (TypeError, ValueError):
        gender = None

    try:
        age = int(params.get("age"))
    except (TypeError, ValueError):
        age = None

    try:
        height = float(params.get("height"))
    except (TypeError, ValueError):
        height = float("nan")

    request_data = {
        "gender": gender,
        "age": age,
        "height": height,
    }

    if gender is None:
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. gender is required.",
            },
        )
    elif age is None:
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. age is required.",
            },
        )
    elif math.isnan(height):
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. height is required.",
            },
        )
    elif gender not in (0, 1):
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. gender is 0 or 1.",
            },
        )
    elif age < 0:
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. age is positive.",
            },
        )
    elif height <= 0:
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. height is positive.",
            },
        )

    if gender == 0:
        gender_info = {
            "base_breathing_capacity_def": 3500,
            "base_val_def": 27.63,
            "minus_def": 0.122,
        }
    else:
        gender_info = {
            "base_breathing_capacity_def": 2500,
            "base_val_def": 21.78,
            "minus_def": 0.101,
        }

    calc_age = _floor_3(gender_info["minus_def"] * age)
    predict_breathing_capacity = _floor_3(
        (gender_info["base_val_def"] - calc_age) * height
    )
    breathing_capacity_percentage = _floor_3(
        gender_info["base_breathing_capacity_def"]
        / predict_breathing_capacity
        * 100
    )

    return _response(
        200,
        {
            "result": 0,
            "breathing_capacity_calc_result": {
                "predict_breathing_capacity": predict_breathing_capacity,
                "breathing_capacity_percentage": breathing_capacity_percentage,
            },
            "user_data": request_data,
        },
    )
