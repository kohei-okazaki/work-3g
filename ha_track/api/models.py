from datetime import datetime
from mongoengine import (
    Document,
    FloatField,
    DateTimeField,
    LongField,
    ReferenceField,
    SequenceField,
    CASCADE,
)


class HealthInfo(Document):
    # 健康情報モデル
    meta = {
        "collection": "health_info",  # コレクション名（任意）
        "indexes": [
            "seq_health_info_id",
            "-created_at",
        ],
    }

    # 健康情報ID（整数 15 桁想定 → LongField で対応）
    seq_health_info_id = LongField(required=True)
    # 身長
    height = FloatField()
    # 体重
    weight = FloatField()
    # BMI
    bmi = FloatField()
    # 標準体重
    standard_weight = FloatField()
    # 健康情報登録日時
    # auto_now_add 相当は default=datetime.utcnow を使う
    created_at = DateTimeField(default=datetime.utcnow)

    def __str__(self):
        return (
            f"HealthInfo height={self.height}, weight={self.weight}, "
            f"bmi={self.bmi}, standard_weight={self.standard_weight}"
        )


class HealthTrackLog(Document):
    # 健康追跡ログモデル
    meta = {
        "collection": "health_track_log",
        "indexes": [
            "seq_user_id",
            "-created_at",
            {"fields": ["health_info"], "unique": True},  # OneToOne 制約
        ],
    }

    # シーケンスID（RDB の AUTO INCREMENT 代替）
    # ※ 既存の BigAutoField 相当。不要なら削除して ObjectId に任せてもOK
    id = SequenceField(primary_key=True)
    # ユーザID
    seq_user_id = LongField(required=True)
    # 連携日時
    synced_at = DateTimeField(default=datetime.utcnow)
    # 登録日時
    created_at = DateTimeField(default=datetime.utcnow)

    # 健康情報との 1 対 1（ユニーク制約で実現）
    health_info = ReferenceField(
        HealthInfo,
        reverse_delete_rule=CASCADE,  # HealthInfo 削除時に一緒に削除
        required=True,
        unique=True,  # OneToOne の肝
    )

    def __str__(self):
        return f"HealthTrackLog for {self.seq_user_id} on {self.created_at}"
