import os

import boto3


def get_dynamodb_resource():
    resource_options = {
        "region_name": "ap-northeast-1",
    }
    endpoint_url = os.getenv("DYNAMODB_ENDPOINT_URL")
    if endpoint_url:
        resource_options["endpoint_url"] = endpoint_url

    return boto3.resource("dynamodb", **resource_options)


def put_dynamo_db(table_name, item):
    table = get_dynamodb_resource().Table(table_name)
    table.put_item(Item=item)
