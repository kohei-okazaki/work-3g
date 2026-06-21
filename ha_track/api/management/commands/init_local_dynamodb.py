import os
import time

from api.util.dynamo_util import get_dynamodb_resource
from botocore.exceptions import BotoCoreError, ClientError
from django.conf import settings
from django.core.management.base import BaseCommand, CommandError


class Command(BaseCommand):
    help = "Create the health_info table in DynamoDB Local if it does not exist."

    def handle(self, *args, **options):
        if not os.getenv("DYNAMODB_ENDPOINT_URL"):
            raise CommandError(
                "DYNAMODB_ENDPOINT_URL is required. "
                "This command must only be used with DynamoDB Local."
            )

        dynamodb = self._wait_for_dynamodb()
        table_name = settings.HEALTH_INFO_DYNAMODB_TABLE_NAME

        try:
            table = dynamodb.create_table(
                TableName=table_name,
                KeySchema=[
                    {"AttributeName": "seq_user_id", "KeyType": "HASH"},
                    {"AttributeName": "created_at_epoch", "KeyType": "RANGE"},
                ],
                AttributeDefinitions=[
                    {"AttributeName": "seq_user_id", "AttributeType": "N"},
                    {"AttributeName": "created_at_epoch", "AttributeType": "N"},
                ],
                BillingMode="PAY_PER_REQUEST",
            )
            table.wait_until_exists()
            self.stdout.write(
                self.style.SUCCESS(f"Created DynamoDB Local table: {table_name}")
            )
        except ClientError as error:
            if error.response.get("Error", {}).get("Code") != "ResourceInUseException":
                raise
            self.stdout.write(f"DynamoDB Local table already exists: {table_name}")

    def _wait_for_dynamodb(self):
        last_error = None

        for _ in range(30):
            try:
                dynamodb = get_dynamodb_resource()
                dynamodb.meta.client.list_tables(Limit=1)
                return dynamodb
            except (BotoCoreError, ClientError) as error:
                last_error = error
                time.sleep(1)

        raise CommandError(f"DynamoDB Local did not become ready: {last_error}")
