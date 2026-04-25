from datetime import datetime

from django.utils import timezone
from rest_framework import serializers


class HealthInfoItemSerializer(serializers.Serializer):
    seq_health_info_id = serializers.IntegerField(
        label="health info ID", required=True
    )
    height = serializers.FloatField(label="height", required=True)
    weight = serializers.FloatField(label="weight", required=True)
    bmi = serializers.FloatField(label="BMI", required=True)
    standard_weight = serializers.FloatField(label="standard weight", required=True)
    created_at = serializers.CharField(label="created at", required=True)

    def validate_created_at(self, value: str):
        if not value:
            return None

        try:
            dt = datetime.strptime(value, "%Y/%m/%d %H:%M:%S")
            return timezone.make_aware(dt, timezone.get_current_timezone())
        except Exception:
            raise serializers.ValidationError(
                "created_at must be specified in 'YYYY/MM/DD HH:MM:SS' format"
            )


class HealthInfoPayloadSerializer(serializers.Serializer):
    seq_user_id = serializers.IntegerField(label="user ID", required=True)
    health_infos = HealthInfoItemSerializer(label="health info list", many=True)
