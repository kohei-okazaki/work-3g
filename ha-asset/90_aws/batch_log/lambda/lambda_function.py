import json
import os
import boto3
from botocore.exceptions import ClientError

dynamodb = boto3.resource("dynamodb")
table = dynamodb.Table("batch_log")

COLUMNS = [
    "transaction_id",
    "batch_name",
    "job_result",
    "start_date",
    "end_date",
]

def lambda_handler(event, context):
    failures = []

    for r in event.get("Records", []):
        message_id = r["messageId"]

        try:
            # SQSのmessage body(JSON)
            payload = json.loads(r["body"])
            item = {column: payload.get(column) for column in COLUMNS if payload.get(column) is not None}

            # 冪等：同じtransaction_idは二重登録しない
            table.put_item(
                Item=item,
                ConditionExpression="attribute_not_exists(transaction_id)"
            )

        except ClientError as e:
            # 既に存在（ConditionalCheckFailed）は「成功扱い」にしたいことが多い
            if e.response.get("Error", {}).get("Code") == "ConditionalCheckFailedException":
                # 重複なので無視（成功扱い）
                continue
            print(f"ClientError messageId={message_id}: {e}")
            failures.append({"itemIdentifier": message_id})

        except Exception as e:
            print(f"Error messageId={message_id}: {e}")
            failures.append({"itemIdentifier": message_id})

    # 部分失敗を返す（成功分は削除、失敗分だけ再試行へ） :contentReference[oaicite:7]{index=7}
    return {"batchItemFailures": failures}
