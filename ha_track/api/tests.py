from datetime import datetime
from datetime import timezone as dt_timezone
from decimal import Decimal
from unittest.mock import Mock, patch

from api.serializers import HealthInfoPayloadSerializer
from api.util.dynamo_util import get_dynamodb_resource, put_dynamo_db
from api.views import HealthInfoAPIView
from django.test import SimpleTestCase, override_settings
from django.urls import resolve, reverse
from django.utils import timezone
from rest_framework import status
from rest_framework.test import APIRequestFactory


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
        get_dynamodb_resource()

        mock_resource.assert_called_once_with("dynamodb", region_name="ap-northeast-1")
