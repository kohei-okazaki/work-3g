import json
import math


HEADERS = {
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json",
}


def _response(status_code: int, body: dict) -> dict:
    return {
        "statusCode": status_code,
        "headers": HEADERS,
        "body": json.dumps(body, ensure_ascii=False),
    }


def _floor_3(value: float) -> float:
    return math.floor(value * 1000) / 1000


def lambda_handler(event, context):
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
