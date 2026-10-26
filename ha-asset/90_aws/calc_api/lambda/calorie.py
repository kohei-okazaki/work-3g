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
    """クエリパラメータから基礎代謝量と1日の消費カロリーを計算する。

    Args:
        event (dict): API Gatewayのイベント。queryStringParametersに以下を含む。
            gender: 性別（0: 男性、1: 女性）。
            age: 年齢（歳、0以上の整数）。
            height: 身長（cm、正の数）。
            weight: 体重（kg、正の数）。
            life_work_metabolism: 生活活動代謝（kcal、正の数）。
        context (object): Lambdaの実行コンテキスト。この関数では使用しない。

    Returns:
        dict: 成功時はHTTP 200で基礎代謝量と1日の消費カロリー（kcal）、
            入力値を返す。入力不備時はHTTP 400でエラー詳細を返す。
            本文はJSON文字列として格納する。
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

    try:
        weight = float(params.get("weight"))
    except (TypeError, ValueError):
        weight = float("nan")

    try:
        life_work_metabolism = float(params.get("life_work_metabolism"))
    except (TypeError, ValueError):
        life_work_metabolism = float("nan")

    request_data = {
        "gender": gender,
        "age": age,
        "height": height,
        "weight": weight,
        "life_work_metabolism": life_work_metabolism,
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
    elif math.isnan(weight):
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. weight is required.",
            },
        )
    elif math.isnan(life_work_metabolism):
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. life_work_metabolism is required.",
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
    elif weight <= 0:
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. weight is positive.",
            },
        )
    elif life_work_metabolism <= 0:
        return _response(
            400,
            {
                "result": 1,
                "detail": "Invalid parameters. life_work_metabolism is positive.",
            },
        )

    if gender == 0:
        gender_info = {
            "weight_def": 13.397,
            "height_def": 4.799,
            "age_def": 5.677,
            "adjust_def": 88.362,
        }
    else:
        gender_info = {
            "weight_def": 9.247,
            "height_def": 3.098,
            "age_def": 4.33,
            "adjust_def": 447.593,
        }

    calc_weight = _floor_3(gender_info["weight_def"] * weight)
    calc_height = _floor_3(gender_info["height_def"] * height)
    calc_age = _floor_3(gender_info["age_def"] * age)

    base_metabolism = _floor_3(
        calc_weight + calc_height - calc_age + gender_info["adjust_def"]
    )
    lost_calorie_per_day = _floor_3(base_metabolism + life_work_metabolism)

    return _response(
        200,
        {
            "result": 0,
            "calorie_calc_result": {
                "base_metabolism": base_metabolism,
                "lost_calorie_per_day": lost_calorie_per_day,
            },
            "user_data": request_data,
        },
    )
