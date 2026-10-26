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
    """身長と体重からBMIと標準体重を計算して返す。

    Args:
        event (dict): queryStringParametersに身長height（cm）と体重weight（kg）を
            含むAPI Gatewayイベント。
        context (object): Lambdaの実行コンテキスト。この関数では使用しない。

    Returns:
        dict: 正常時はHTTP 200と身長、体重、BMI、標準体重（kg）を含む
            JSONレスポンス。入力不備の場合はHTTP 400とエラー詳細を返す。
    """
    params = event.get("queryStringParameters") or {}

    try:
        height = float(params.get("height"))
    except (TypeError, ValueError):
        height = float("nan")

    try:
        weight = float(params.get("weight"))
    except (TypeError, ValueError):
        weight = float("nan")

    if math.isnan(height):
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

    meter_height = height / 100
    bmi = _floor_3(weight / (meter_height * meter_height))
    standard_weight = _floor_3(22 * (meter_height * meter_height))

    return _response(
        200,
        {
            "result": 0,
            "basic_health_info": {
                "height": height,
                "weight": weight,
                "bmi": bmi,
                "standard_weight": standard_weight,
            },
        },
    )
