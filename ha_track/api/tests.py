import os
from datetime import datetime
from datetime import timezone as dt_timezone
from decimal import Decimal
from unittest.mock import Mock, patch

from api.management.commands.init_local_dynamodb import Command
from api.serializers import HealthInfoPayloadSerializer
from api.util.dynamo_util import get_dynamodb_resource, put_dynamo_db
from api.views import HealthInfoAPIView
from django.core.exceptions import ImproperlyConfigured
from django.core.management.base import CommandError
from django.test import SimpleTestCase, override_settings
from django.urls import resolve, reverse
from django.utils import timezone
from rest_framework import status
from rest_framework.test import APIRequestFactory

from ha_track import settings


@override_settings(TIME_ZONE="Asia/Tokyo", USE_TZ=True)
class HealthInfoPayloadSerializerTest(SimpleTestCase):

    def test_valid_payload_converts_created_at_to_aware_datetime(self):
        serializer = HealthInfoPayloadSerializer(data=self._payload())

        self.assertTrue(serializer.is_valid(), serializer.errors)

        created_at = serializer.validated_data["health_infos"][0]["created_at"]
        self.assertTrue(timezone.is_aware(created_at))
        self.assertEqual(
            datetime(2024, 12, 1, 12, 34, 56),
            created_at.replace(tzinfo=None),
        )
        self.assertEqual("Asia/Tokyo", created_at.tzinfo.key)

    def test_invalid_created_at_format_is_rejected(self):
        payload = self._payload(created_at="2024-12-01 12:34:56")
        serializer = HealthInfoPayloadSerializer(data=payload)

        self.assertFalse(serializer.is_valid())
        self.assertIn("created_at", serializer.errors["health_infos"][0])

    def test_missing_required_payload_field_is_rejected(self):
        payload = self._payload()
        payload.pop("seq_user_id")
        serializer = HealthInfoPayloadSerializer(data=payload)

        self.assertFalse(serializer.is_valid())
        self.assertIn("seq_user_id", serializer.errors)

    def _payload(self, created_at="2024/12/01 12:34:56"):
        return {
            "seq_user_id": 100,
            "health_infos": [
                {
                    "seq_health_info_id": 200,
                    "height": 170.5,
                    "weight": 70.2,
                    "bmi": 24.2,
                    "standard_weight": 63.6,
                    "created_at": created_at,
                }
            ],
        }


@override_settings(TIME_ZONE="Asia/Tokyo", USE_TZ=True)
class HealthInfoAPIViewTest(SimpleTestCase):

    def setUp(self):
        self.factory = APIRequestFactory()
        self.view = HealthInfoAPIView.as_view()

    def test_url_resolves_to_health_info_view(self):
        resolver_match = resolve(reverse("healthinfo"))

        self.assertIs(resolver_match.func.view_class, HealthInfoAPIView)

    @patch("api.views.timezone.now")
    @patch("api.views.put_dynamo_db")
    def test_post_registers_health_info_to_dynamodb(self, mock_put_dynamo_db, mock_now):
        mock_now.return_value = datetime(2025, 1, 2, 3, 4, 5, tzinfo=dt_timezone.utc)

        response = self._post(self._payload())

        self.assertEqual(status.HTTP_200_OK, response.status_code)
        self.assertEqual(0, response.data["result"])
        self.assertEqual("2025/01/02 12:04:05", response.data["synced_at"])

        mock_put_dynamo_db.assert_called_once()
        call_kwargs = mock_put_dynamo_db.call_args.kwargs
        self.assertEqual("health_info", call_kwargs["table_name"])

        item = call_kwargs["item"]
        self.assertEqual(200, item["seq_health_info_id"])
        self.assertEqual(1733024096, item["created_at_epoch"])
        self.assertEqual(100, item["seq_user_id"])
        self.assertEqual(Decimal("170.5"), item["height"])
        self.assertEqual(Decimal("70.2"), item["weight"])
        self.assertEqual(Decimal("24.2"), item["bmi"])
        self.assertEqual(Decimal("63.6"), item["standard_weight"])
        self.assertEqual("2024-12-01T03:34:56+00:00", item["created_at"])

    @patch("api.views.put_dynamo_db")
    def test_post_returns_bad_request_when_payload_is_invalid(self, mock_put_dynamo_db):
        response = self._post(self._payload(created_at="2024-12-01 12:34:56"))

        self.assertEqual(status.HTTP_400_BAD_REQUEST, response.status_code)
        self.assertEqual(1, response.data["result"])
        self.assertIn("created_at", response.data["error_message"]["health_infos"][0])
        mock_put_dynamo_db.assert_not_called()

    @patch(
        "api.views.put_dynamo_db", side_effect=RuntimeError("DynamoDB is unavailable")
    )
    def test_post_returns_bad_request_when_dynamodb_write_fails(
        self, mock_put_dynamo_db
    ):
        response = self._post(self._payload())

        self.assertEqual(status.HTTP_400_BAD_REQUEST, response.status_code)
        self.assertEqual(1, response.data["result"])
        self.assertEqual(
            [{"index": 0, "error": "unexpected", "detail": "DynamoDB is unavailable"}],
            response.data["error_message"],
        )
        mock_put_dynamo_db.assert_called_once()

    def _post(self, payload):
        request = self.factory.post(reverse("healthinfo"), data=payload, format="json")
        return self.view(request)

    def _payload(self, created_at="2024/12/01 12:34:56"):
        return {
            "seq_user_id": 100,
            "health_infos": [
                {
                    "seq_health_info_id": 200,
                    "height": 170.5,
                    "weight": 70.2,
                    "bmi": 24.2,
                    "standard_weight": 63.6,
                    "created_at": created_at,
                }
            ],
        }


class DynamoUtilTest(SimpleTestCase):

    @patch("api.util.dynamo_util.get_dynamodb_resource")
    def test_put_dynamo_db_puts_item_to_named_table(self, mock_get_dynamodb_resource):
        dynamodb = Mock()
        table = Mock()
        mock_get_dynamodb_resource.return_value = dynamodb
        dynamodb.Table.return_value = table
        item = {"seq_health_info_id": 200}

        put_dynamo_db("health_info", item)

        mock_get_dynamodb_resource.assert_called_once_with()
        dynamodb.Table.assert_called_once_with("health_info")
        table.put_item.assert_called_once_with(Item=item)

    @patch("api.util.dynamo_util.boto3.resource")
    def test_get_dynamodb_resource_uses_ap_northeast_1(self, mock_resource):
        with patch.dict(os.environ, {}, clear=True):
            get_dynamodb_resource()

        mock_resource.assert_called_once_with("dynamodb", region_name="ap-northeast-1")

    @patch("api.util.dynamo_util.boto3.resource")
    def test_get_dynamodb_resource_uses_local_endpoint_from_environment(
        self, mock_resource
    ):
        with patch.dict(
            os.environ,
            {
                "AWS_REGION": "ap-northeast-1",
                "DYNAMODB_ENDPOINT_URL": "http://dynamodb-local:8000",
            },
            clear=True,
        ):
            get_dynamodb_resource()

        mock_resource.assert_called_once_with(
            "dynamodb",
            region_name="ap-northeast-1",
            endpoint_url="http://dynamodb-local:8000",
        )


class InitLocalDynamoDBCommandTest(SimpleTestCase):

    def test_rejects_execution_without_local_endpoint(self):
        with patch.dict(os.environ, {}, clear=True):
            with self.assertRaises(CommandError):
                Command().handle()

    @override_settings(HEALTH_INFO_DYNAMODB_TABLE_NAME="health_info")
    def test_creates_table_with_existing_key_schema(self):
        dynamodb = Mock()
        table = Mock()
        dynamodb.create_table.return_value = table
        command = Command()
        command._wait_for_dynamodb = Mock(return_value=dynamodb)

        with patch.dict(
            os.environ,
            {"DYNAMODB_ENDPOINT_URL": "http://dynamodb-local:8000"},
            clear=True,
        ):
            command.handle()

        dynamodb.create_table.assert_called_once_with(
            TableName="health_info",
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
        table.wait_until_exists.assert_called_once_with()


class DynamoDBTableNameSettingsTest(SimpleTestCase):

    def test_local_environment_uses_fixed_table_name(self):
        with patch.object(settings, "APP_ENV", "local"):
            with patch.dict(
                os.environ,
                {"HEALTH_INFO_DYNAMODB_TABLE_NAME": "ignored_table"},
                clear=True,
            ):
                table_name = settings.get_health_info_dynamodb_table_name()

        self.assertEqual("health_info", table_name)

    def test_non_local_environment_uses_environment_variable(self):
        with patch.object(settings, "APP_ENV", "dev"):
            with patch.dict(
                os.environ,
                {"HEALTH_INFO_DYNAMODB_TABLE_NAME": "health_info_dev"},
                clear=True,
            ):
                table_name = settings.get_health_info_dynamodb_table_name()

        self.assertEqual("health_info_dev", table_name)

    def test_non_local_environment_requires_table_name(self):
        with patch.object(settings, "APP_ENV", "prd"):
            with patch.dict(os.environ, {}, clear=True):
                with self.assertRaises(ImproperlyConfigured):
                    settings.get_health_info_dynamodb_table_name()
