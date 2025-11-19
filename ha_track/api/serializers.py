from rest_framework import serializers
from django.utils import timezone
from datetime import datetime


class HealthInfoItemSerializer(serializers.Serializer):
    """
    健康情報シリアライザー
    """

    seq_health_info_id = serializers.IntegerField(required=True)
    height = serializers.FloatField(required=True)
    weight = serializers.FloatField(required=True)
    bmi = serializers.FloatField(required=True)
    standard_weight = serializers.FloatField(required=True)
    created_at = serializers.CharField(required=True)

    def validate_created_at(self, value: str):
        """created_at のバリデーション

        Args:
            value (str): created_at の値
        """
        if not value:
            return None

        try:
            # "YYYY/MM/DD HH:MM:SS" のみを受け付ける
            dt = datetime.strptime(value, "%Y/%m/%d %H:%M:%S")
            # naive datetime を現在のタイムゾーンで aware 化
            dt = timezone.make_aware(dt, timezone.get_current_timezone())
            return dt
        except Exception:
            raise serializers.ValidationError(
                "created_at は 'YYYY/MM/DD HH:MM:SS' 形式で指定してください"
            )


class HealthInfoPayloadSerializer(serializers.Serializer):
    """
    健康情報データ部シリアライザー
    """

    seq_user_id = serializers.IntegerField()
    health_infos = HealthInfoItemSerializer(many=True)
