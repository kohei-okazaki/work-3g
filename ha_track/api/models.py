from django.db import models

class HealthInfo(models.Model):

    # 身長
    height = models.FloatField()
    # 体重
    weight = models.FloatField()
    # BMI
    bmi = models.FloatField()
    # 標準体重
    standard_weight = models.FloatField()
    # 健康情報登録日時
    created_at= models.DateTimeField()

    def __str__(self):
        return f"HealthTrackLog height = {self.height}, weight = {self.weight}, bmi = {self.bmi}, standard_weight = {self.standard_weight}"

class HealthTrackLog(models.Model):

    # シーケンスID
    id = models.BigAutoField(primary_key=True)
    # ユーザーID
    seq_user_id = models.DecimalField(max_digits=15, decimal_places=0)
    # 連携日時
    synced_at = models.DateTimeField()
    # 登録日時
    created_at = models.DateTimeField(auto_now_add=True)
    
    # 健康情報との1対1のリレーション
    health_info = models.OneToOneField(
        HealthInfo,
        on_delete=models.CASCADE,
        related_name="health_track_log"
    )

    def __str__(self):
        return f"HealthTrackLog for {self.seq_user_id} on {self.created_at}"

