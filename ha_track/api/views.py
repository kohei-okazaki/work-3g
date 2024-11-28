from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import HealthData
from .serializers import HealthDataSerializer

class HealthDataAPIView(APIView):
    def post(self, request):
        serializer = HealthDataSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)