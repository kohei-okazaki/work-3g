data "aws_availability_zones" "available" {
  state = "available"
}

data "aws_caller_identity" "current" {}

data "aws_partition" "current" {}

data "aws_region" "current" {}

locals {
  resource_prefix        = "${var.project_name}-${var.app_env}"
  raw_resource_dns_label = trim(replace(lower(local.resource_prefix), "/[^a-z0-9-]/", "-"), "-")
  resource_dns_label     = local.raw_resource_dns_label != "" ? local.raw_resource_dns_label : "healthinfo-app-${var.app_env}"

  az_names = slice(data.aws_availability_zones.available.names, 0, var.az_count)

  service_discovery_namespace_name = var.service_discovery_namespace_name != "" ? var.service_discovery_namespace_name : "${local.resource_dns_label}.local"

  db_master_password_parameter_name      = var.db_master_password_parameter_name != "" ? var.db_master_password_parameter_name : "/${local.resource_prefix}/db/master/password"
  db_app_password_parameter_name         = var.db_app_password_parameter_name != "" ? var.db_app_password_parameter_name : "/${local.resource_prefix}/db/app/password"
  track_django_secret_key_parameter_name = var.track_django_secret_key_parameter_name != "" ? var.track_django_secret_key_parameter_name : "/${local.resource_prefix}/ha-track/django-secret-key"

  db_master_password_parameter_selector      = var.db_master_password_parameter_version > 0 ? "${local.db_master_password_parameter_name}:${var.db_master_password_parameter_version}" : local.db_master_password_parameter_name
  db_app_password_parameter_resource         = trim(local.db_app_password_parameter_name, "/")
  app_ssm_parameter_prefix_resource          = trim(var.app_ssm_parameter_prefix, "/")
  track_django_secret_key_parameter_resource = trim(local.track_django_secret_key_parameter_name, "/")

  db_app_password_parameter_arn         = "arn:${data.aws_partition.current.partition}:ssm:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:parameter/${local.db_app_password_parameter_resource}"
  app_ssm_parameter_prefix_arn          = "arn:${data.aws_partition.current.partition}:ssm:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:parameter/${local.app_ssm_parameter_prefix_resource}/*"
  track_django_secret_key_parameter_arn = "arn:${data.aws_partition.current.partition}:ssm:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:parameter/${local.track_django_secret_key_parameter_resource}"

  db_url = "jdbc:mysql://${aws_db_instance.database.address}:${aws_db_instance.database.port}/${var.db_name}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo"

  dashboard_image_uri = "${aws_ecr_repository.dashboard.repository_url}:${var.image_tag}"
  api_image_uri       = "${aws_ecr_repository.api.repository_url}:${var.image_tag}"
  root_api_image_uri  = "${aws_ecr_repository.root_api.repository_url}:${var.image_tag}"
  track_image_uri     = "${aws_ecr_repository.track.repository_url}:${var.image_tag}"
  batch_image_uri     = "${aws_ecr_repository.batch.repository_url}:${var.image_tag}"

  dashboard_container_port = 80
  api_container_port       = 80
  root_api_container_port  = 80
  track_container_port     = 80

  api_internal_base_url         = "http://${var.api_service_discovery_name}.${local.service_discovery_namespace_name}/api/"
  root_api_internal_base_url    = "http://${var.root_api_service_discovery_name}.${local.service_discovery_namespace_name}/api/root/"
  track_internal_base_url       = "http://${var.track_service_discovery_name}.${local.service_discovery_namespace_name}/api/"
  dashboard_health_info_api_url = var.health_info_api_url != "" ? var.health_info_api_url : local.api_internal_base_url
  effective_root_api_url        = var.root_api_url != "" ? var.root_api_url : local.root_api_internal_base_url
  effective_track_api_url       = var.health_info_track_api_url != "" ? var.health_info_track_api_url : local.track_internal_base_url
  track_django_allowed_hosts    = var.track_django_allowed_hosts != "" ? var.track_django_allowed_hosts : "${var.track_service_discovery_name}.${local.service_discovery_namespace_name}"

  dashboard_environment = concat(
    [
      { name = "SPRING_PROFILES_ACTIVE", value = var.app_env },
      { name = "SPRING_FLYWAY_ENABLED", value = "true" },
      { name = "SPRING_FLYWAY_LOCATIONS", value = "classpath:/db/migration" },
      { name = "DB_URL", value = local.db_url },
      { name = "DB_USER", value = var.db_app_username },
      { name = "SERVER_PORT", value = tostring(local.dashboard_container_port) },
      { name = "API_LOG_QUEUE_NAME", value = aws_sqs_queue.api_log.name },
      { name = "HEALTHINFO_API_URL", value = local.dashboard_health_info_api_url },
      { name = "ROOT_API_URL", value = local.effective_root_api_url },
    ],
    var.health_info_dashboard_url != "" ? [{ name = "HEALTHINFO_DASHBOARD_URL", value = var.health_info_dashboard_url }] : [],
    [{ name = "HEALTHINFO_TRACK_API_URL", value = local.effective_track_api_url }],
  )

  api_environment = concat(
    [
      { name = "SPRING_PROFILES_ACTIVE", value = var.app_env },
      { name = "SPRING_FLYWAY_ENABLED", value = "false" },
      { name = "DB_URL", value = local.db_url },
      { name = "DB_USER", value = var.db_app_username },
      { name = "SERVER_PORT", value = tostring(local.api_container_port) },
      { name = "API_LOG_QUEUE_NAME", value = aws_sqs_queue.api_log.name },
      { name = "ROOT_API_URL", value = local.effective_root_api_url },
    ],
    var.health_info_dashboard_url != "" ? [{ name = "HEALTHINFO_DASHBOARD_URL", value = var.health_info_dashboard_url }] : [],
    var.health_info_api_url != "" ? [{ name = "HEALTHINFO_API_URL", value = var.health_info_api_url }] : [],
    [{ name = "HEALTHINFO_TRACK_API_URL", value = local.effective_track_api_url }],
  )

  root_api_environment = concat(
    [
      { name = "SPRING_PROFILES_ACTIVE", value = var.app_env },
      { name = "SPRING_FLYWAY_ENABLED", value = "false" },
      { name = "DB_URL", value = local.db_url },
      { name = "DB_USER", value = var.db_app_username },
      { name = "SERVER_PORT", value = tostring(local.root_api_container_port) },
      { name = "API_LOG_QUEUE_NAME", value = aws_sqs_queue.api_log.name },
      { name = "FRONT_URL", value = var.root_front_url },
      { name = "AWS_S3_BACKET", value = var.app_data_bucket_name },
    ],
    var.health_info_dashboard_url != "" ? [{ name = "HEALTHINFO_DASHBOARD_URL", value = var.health_info_dashboard_url }] : [],
    var.health_info_api_url != "" ? [{ name = "HEALTHINFO_API_URL", value = var.health_info_api_url }] : [],
    [{ name = "HEALTHINFO_TRACK_API_URL", value = local.effective_track_api_url }],
  )

  track_environment = [
    { name = "APP_ENV", value = var.app_env },
    { name = "HEALTH_INFO_DYNAMODB_TABLE_NAME", value = aws_dynamodb_table.health_info.name },
    { name = "DJANGO_ALLOWED_HOSTS", value = local.track_django_allowed_hosts },
    { name = "DJANGO_DEBUG", value = "false" },
  ]

  batch_environment = concat(
    [
      { name = "TZ", value = "Asia/Tokyo" },
      { name = "SPRING_PROFILES_ACTIVE", value = var.app_env },
      { name = "SPRING_FLYWAY_ENABLED", value = "false" },
      { name = "DB_URL", value = local.db_url },
      { name = "DB_USER", value = var.db_app_username },
      { name = "API_LOG_QUEUE_NAME", value = aws_sqs_queue.api_log.name },
      { name = "HEALTHINFO_API_URL", value = local.dashboard_health_info_api_url },
      { name = "ROOT_API_URL", value = local.effective_root_api_url },
      { name = "HEALTHINFO_TRACK_API_URL", value = local.effective_track_api_url },
    ],
    var.health_info_dashboard_url != "" ? [{ name = "HEALTHINFO_DASHBOARD_URL", value = var.health_info_dashboard_url }] : [],
  )

  common_tags = {
    Project     = var.project_name
    Environment = var.app_env
    ManagedBy   = "terraform"
  }
}
