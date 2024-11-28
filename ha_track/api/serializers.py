from datetime import datetime
from rest_framework import serializers
from .models import HealthData

class HealthDataSerializer(serializers.ModelSerializer):
    
    health_data_created_at = serializers.DateTimeField(
        input_formats = [
            "%Y/%m/%d %H:%M:%S", 
        ]
    )
    synced_at = serializers.DateTimeField(required=False)

    class Meta:
        model = HealthData
        fields = "__all__"
        # synced_atはクライアントから送信できない
        read_only_fields = ['synced_at']
    
    def validate(self, data):
        data['synced_at'] = datetime.now()
        return data