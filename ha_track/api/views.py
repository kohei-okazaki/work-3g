import logging
from django.utils.timezone import localtime
from django.utils.timezone import now
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializers import HealthTrackLogSerializer

# ログ出力用
logger = logging.getLogger(__name__)


class HealthInfoAPIView(APIView):

    def post(self, request):

        print(f"req_data: {request.data}")
        logger.info(f"req_data: {request.data}")

        # seq_user_id と health_infos を取得
        seq_user_id = request.data.get('seq_user_id')
        health_infos = request.data.get('health_infos', [])

        # 健康情報を順に処理
        errors = []

        synced_at = now()
        for health_info_data in health_infos:
            data = {
                'seq_user_id': seq_user_id,
                'synced_at': localtime(synced_at),
                'health_info': health_info_data,
            }
            serializer = HealthTrackLogSerializer(data=data)
            if serializer.is_valid():
                serializer.save()
            else:
                errors.append(serializer.errors)

        # レスポンスの構築
        if errors:
            error_response = {
                "result": 1,
                "error_message": "Healthinfo migrate fail"
            }
            return Response(error_response, status=status.HTTP_400_BAD_REQUEST)
        
        success_response = {
            "result": 0,
            "synced_at": localtime(synced_at).strftime("%Y/%m/%d %H:%M:%S")
        }
        return Response(success_response, status=status.HTTP_200_OK)
