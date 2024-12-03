from django.utils.timezone import now
from rest_framework import serializers
from .models import HealthInfo, HealthTrackLog


class HealthInfoSerializer(serializers.ModelSerializer):

    created_at = serializers.DateTimeField(
        input_formats=[
            "%Y/%m/%d %H:%M:%S",
        ],
        format="%Y/%m/%d %H:%M:%S"
    )

    class Meta:
        model = HealthInfo
        fields = ["seq_health_info_id", "height", "weight",
                  "bmi", "standard_weight", "created_at"]


class HealthTrackLogSerializer(serializers.ModelSerializer):

    health_info = HealthInfoSerializer()

    synced_at = serializers.DateTimeField(
        required=False,
        format="%Y/%m/%d %H:%M:%S"
    )

    created_at = serializers.DateTimeField(
        required=False,
        format="%Y/%m/%d %H:%M:%S"
    )

    class Meta:
        model = HealthTrackLog
        fields = ["id", "seq_user_id", "synced_at",
                  "created_at", "health_info"]
        read_only_fields = ["synced_at"]

    def create(self, validated_data):
        # ネストされたデータを分離
        health_info_data = validated_data.pop("health_info")
        health_info = HealthInfo.objects.create(**health_info_data)
        health_track_log = HealthTrackLog.objects.create(
            health_info=health_info, **validated_data)
        return health_track_log

    def validate(self, data):
        data["synced_at"] = now()
        return data
