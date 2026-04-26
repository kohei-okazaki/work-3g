import boto3


def get_dynamodb_resource():
    return boto3.resource("dynamodb", region_name="ap-northeast-1")


def put_dynamo_db(table_name, item):
    table = get_dynamodb_resource().Table(table_name)
    table.put_item(Item=item)
