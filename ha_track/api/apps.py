from django.apps import AppConfig
from django.conf import settings
from mongoengine import connect


class ApiConfig(AppConfig):
    default_auto_field = "django.db.models.BigAutoField"
    name = "api"

    def ready(self):
        cfg = settings.MONGODB_SETTINGS
        connect(
            db=cfg.get("db"),
            host=cfg.get("host"),
            username=cfg.get("username"),
            password=cfg.get("password"),
            authentication_source=cfg.get("auth_source", "admin"),
        )
