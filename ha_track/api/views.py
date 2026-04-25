import logging
from datetime import timezone as dt_timezone
from decimal import Decimal

from django.utils import timezone
from django.utils.timezone import localtime
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from .serializers import HealthInfoPayloadSerializer
from .util.dynamo_util import put_dynamo_db

logger = logging.getLogger(__name__)


class HealthInfoAPIView(APIView):
    """
    Health information registration API.
    Stores health information in DynamoDB.
    """

    def post(self, request):
        logger.info("req_data: %s", request.data)

        serializer = HealthInfoPayloadSerializer(data=request.data)
        if not serializer.is_valid():
            body = {"result": 1, "error_message": serializer.errors}
            return Response(body, status=status.HTTP_400_BAD_REQUEST)

        seq_user_id = serializer.validated_data["seq_user_id"]
        health_infos = serializer.validated_data["health_infos"]

        errors: list = []

        for index, hi in enumerate(health_infos):
            try:
                created_at_aw = hi.get("created_at") or timezone.now()
                created_at_utc = created_at_aw.astimezone(dt_timezone.utc)
                created_at_epoch = int(created_at_utc.timestamp())

                item = {
                    "seq_health_info_id": hi["seq_health_info_id"],
                    "created_at_epoch": created_at_epoch,
                    "seq_user_id": seq_user_id,
                    "height": Decimal(str(hi["height"])),
                    "weight": Decimal(str(hi["weight"])),
                    "bmi": Decimal(str(hi["bmi"])),
                    "standard_weight": Decimal(str(hi["standard_weight"])),
                    "created_at": created_at_utc.isoformat(),
                }
                put_dynamo_db(table_name="health_info", item=item)

            except Exception as e:
                errors.append({"index": index, "error": "unexpected", "detail": str(e)})

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
