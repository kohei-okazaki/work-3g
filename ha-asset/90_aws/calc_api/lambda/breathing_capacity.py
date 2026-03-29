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
