import logging
from decimal import Decimal
from datetime import datetime, timezone as dt_timezone
from django.utils import timezone
from django.utils.timezone import localtime
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from mongoengine import NotUniqueError, ValidationError as MEValidationError

from .models import HealthInfo, HealthTrackLog
from .serializers import HealthInfoPayloadSerializer
from .util.dynamo_util import put_dynamo_db

# Logger
logger = logging.getLogger(__name__)


def to_utc_naive(dt: datetime):
    """
    aware -> UTC naive（MongoEngine既定）
    """
    return dt.astimezone(dt_timezone.utc).replace(tzinfo=None)


class HealthInfoAPIView(APIView):
    """
    - created_at 'YYYY/MM/DD HH:MM:SS' を受け付ける
    - 保存は UTC naive、レスポンスはローカル時刻
    """

    def post(self, request):
        """
        健康情報登録API
        1ユーザの健康情報を登録。（複数件登録可能）
        param request: リクエストデータ
        """

        logger.info("req_data: %s", request.data)

        serializer = HealthInfoPayloadSerializer(data=request.data)
        if not serializer.is_valid():
            # バリデーションエラー
            body = {"result": 1, "error_message": serializer.errors}
            return Response(body, status=status.HTTP_400_BAD_REQUEST)

        if serializer.validated_data["migrate_flg"]:
            # DynamoDB保存処理

            # ユーザID
            seq_user_id = serializer.validated_data["seq_user_id"]
            # 健康情報リスト
            health_infos = serializer.validated_data["health_infos"]

            # エラーリスト
            errors: list = []

            for index, hi in enumerate(health_infos):
                try:
                    created_at_aw = (
                        hi.get("created_at") or timezone.now()
                    )  # aware datetime
                    created_at_utc = created_at_aw.astimezone(dt_timezone.utc)
                    created_at_epoch = int(created_at_utc.timestamp())

                    item = {
                        "seq_user_id": seq_user_id,
                        "created_at_epoch": created_at_epoch,
                        "seq_health_info_id": hi["seq_health_info_id"],
                        "height": Decimal(str(hi["height"])),
                        "weight": Decimal(str(hi["weight"])),
                        "bmi": Decimal(str(hi["bmi"])),
                        "standard_weight": Decimal(str(hi["standard_weight"])),
                        "created_at": created_at_utc.isoformat(),
                    }

                    # 同じ (seq_user_id, created_at_epoch) が既にあれば上書きしない
                    put_dynamo_db(table_name="health_info", item=item)

                except table.meta.client.exceptions.ConditionalCheckFailedException:
                    errors.append(
                        {
                            "index": index,
                            "error": "duplicate key",
                            "detail": "same seq_user_id + created_at_epoch already exists",
                        }
                    )
                except Exception as e:
                    errors.append(
                        {"index": index, "error": "unexpected", "detail": str(e)}
                    )

        else:
            # mongoDB保存処理

            # ユーザID
            seq_user_id = serializer.validated_data["seq_user_id"]
            # 健康情報リスト
            health_infos = serializer.validated_data["health_infos"]

            # エラーリスト
            errors: list = []
            synced_at_aw: datetime = timezone.now()
            synced_at_utc_naive = to_utc_naive(synced_at_aw)

            for index, hi in enumerate(health_infos):
                try:
                    hi_doc = HealthInfo(
                        seq_health_info_id=hi.get("seq_health_info_id"),
                        height=hi.get("height"),
                        weight=hi.get("weight"),
                        bmi=hi.get("bmi"),
                        standard_weight=hi.get("standard_weight"),
                        created_at=(
                            to_utc_naive(hi["created_at"])
                            if hi.get("created_at")
                            else synced_at_utc_naive
                        ),
                    ).save()  # insert

                    HealthTrackLog(
                        seq_user_id=seq_user_id,
                        synced_at=synced_at_utc_naive,
                        created_at=synced_at_utc_naive,
                        health_info=hi_doc,
                    ).save()  # insert

                except NotUniqueError as e:
                    errors.append(
                        {"index": index, "error": "duplicate key", "detail": str(e)}
                    )
                except MEValidationError as e:
                    errors.append(
                        {"index": index, "error": "validation", "detail": e.to_dict()}
                    )
                except Exception as e:
                    errors.append(
                        {"index": index, "error": "unexpected", "detail": str(e)}
                    )

        if errors:
            body = {
                "result": 1,
                "error_message": errors,
            }
            return Response(body, status=status.HTTP_400_BAD_REQUEST)

        return Response(
            {
                "result": 0,
                "synced_at": localtime(timezone.now()).strftime("%Y/%m/%d %H:%M:%S"),
            },
            status=status.HTTP_200_OK,
        )
