"""
DynamoDB操作ユーティリティモジュール
"""

import boto3

# DymamoDB接続情報
dynamodb = boto3.resource("dynamodb", region_name="ap-northeast-1")


def put_dynamo_db(table_name, item):
    """
    DynamoDBにデータを登録する

    @param table_name: テーブル名
    @param item: 登録情報 (辞書形式)
    """
    table = dynamodb.Table(table_name)

    # 同一Keyが存在する場合、上書き更新する
    table.put_item(Item=item)

