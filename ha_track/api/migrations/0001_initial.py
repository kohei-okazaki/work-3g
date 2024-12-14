# Generated by Django 3.0.3 on 2024-11-30 06:55

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='HealthInfo',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('seq_health_info_id', models.DecimalField(decimal_places=0, max_digits=15)),
                ('height', models.FloatField()),
                ('weight', models.FloatField()),
                ('bmi', models.FloatField()),
                ('standard_weight', models.FloatField()),
                ('created_at', models.DateTimeField()),
            ],
        ),
        migrations.CreateModel(
            name='HealthTrackLog',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('seq_user_id', models.DecimalField(decimal_places=0, max_digits=15)),
                ('synced_at', models.DateTimeField()),
                ('created_at', models.DateTimeField(auto_now_add=True)),
                ('health_info', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, related_name='health_track_log', to='api.HealthInfo')),
            ],
        ),
    ]