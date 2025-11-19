import logging
from datetime import timezone as dt_timezone
from django.utils import timezone
from django.utils.timezone import localtime
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from mongoengine import NotUniqueError, ValidationError as MEValidationError

from .models import HealthInfo, HealthTrackLog
from .serializers import HealthInfoPayloadSerializer

# ログ出力用
logger = logging.getLogger(__name__)


def to_utc_naive(dt):
    """aware -> UTC naive（MongoEngine既定）"""
    return dt.astimezone(dt_timezone.utc).replace(tzinfo=None)


class HealthInfoAPIView(APIView):
    """
    - created_at 'YYYY/MM/DD HH:MM:SS' を受け付ける
    - 保存は UTC naive、レスポンスはローカル時刻
    """

    def post(self, request):
        logger.info("req_data: %s", request.data)

        base_ser = HealthInfoPayloadSerializer(data=request.data)
        if not base_ser.is_valid():
            body = {
                "result": 1,
                "error_message": base_ser.errors
            }

            return Response(
                body,
                status=status.HTTP_400_BAD_REQUEST,
            )

        # ユーザID
        seq_user_id = base_ser.validated_data["seq_user_id"]
        # 健康情報リスト
        health_infos = base_ser.validated_data["health_infos"]

        errors = []
        synced_at_aw = timezone.now()
        synced_at_utc_naive = to_utc_naive(synced_at_aw)

        for idx, hi in enumerate(health_infos):
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
                    {"index": idx, "error": "duplicate key", "detail": str(e)}
                )
            except MEValidationError as e:
                errors.append(
                    {"index": idx, "error": "validation", "detail": e.to_dict()}
                )
            except Exception as e:
                errors.append({"index": idx, "error": "unexpected", "detail": str(e)})

        if errors:
            body = {
                "result": 1,
                "error_message": errors,
            }
            return Response(
                body,
                status=status.HTTP_400_BAD_REQUEST,
            )

        return Response(
            {
                "result": 0,
                "synced_at": localtime(synced_at_aw).strftime("%Y/%m/%d %H:%M:%S"),
            },
            status=status.HTTP_200_OK,
        )
