import logging
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializers import HealthTrackLogSerializer


logger = logging.getLogger(__name__)  # ログ出力用


class HealthInfoAPIView(APIView):

    def post(self, request):
        
        print(f"req_data: {request.data}")
        logger.info(f"req_data: {request.data}")  # 受信したデータをログに出力

        serializer = HealthTrackLogSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()

            logger.info("Data is valid and saved successfully.")

            return Response(serializer.data, status=status.HTTP_200_OK)

        logger.error(f"Validation errors: {serializer.errors}")

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
