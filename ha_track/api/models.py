from django.db import models

class HealthData(models.Model):

    # シーケンスID
    id = models.BigAutoField(primary_key=True)
    # ユーザーID
    seq_user_id = models.DecimalField(max_digits=15, decimal_places=0)
    # 健康情報登録日時
    health_data_created_at= models.DateTimeField()
    # 連携日時
    synced_at = models.DateTimeField()
    # 身長
    height = models.FloatField()
    # 体重
    weight = models.FloatField()
    # BMI
    bmi = models.FloatField()
    # 標準体重
    standard_weight = models.FloatField()
    # 登録日時
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"HealthData for {self.seq_user_id} on {self.created_at}"
