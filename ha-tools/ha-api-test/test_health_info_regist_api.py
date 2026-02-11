import os
import time
import requests
import pytest

"""
健康情報登録APIの自動テスト
"""
BASE_URL = "http://localhost:8081"

headers = {
    "Content-Type": "application/json",
    "Accept-Charset": "utf-8",
    "API-KEY": "d5cd823555e5d392d9bd2b7121a4797aa6022ca055aaa4dfe1ec844e918df2b9",
}


def test_health_info_registration():
    """
    健康情報登録APIの動作確認テスト
    """

    payload = {
        "height": 170.00,
        "weight": 60,
        "test_mode": 0,
        "transaction_id": 999999999,
    }
    
    post_url = f"{BASE_URL}/api/1/healthinfo"
    post_res = requests.post(post_url, json=payload, headers=headers, timeout=10)

    if post_res.status_code != 200:
        pytest.fail("POST request failed with status code {}".format(post_res.status_code))
        
    post_json = post_res.json()