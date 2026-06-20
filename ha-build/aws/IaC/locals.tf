data "aws_availability_zones" "available" {
  state = "available"
}

data "aws_caller_identity" "current" {}

data "aws_partition" "current" {}

data "aws_region" "current" {}

locals {
  raw_project_dns_label = trim(replace(lower(var.project_name), "/[^a-z0-9-]/", "-"), "-")
  project_dns_label     = local.raw_project_dns_label != "" ? local.raw_project_dns_label : "healthinfo-app"

  az_names = slice(data.aws_availability_zones.available.names, 0, var.az_count)

  service_discovery_namespace_name = var.service_discovery_namespace_name != "" ? var.service_discovery_namespace_name : "${local.project_dns_label}.local"

  db_master_password_parameter_name = var.db_master_password_parameter_name != "" ? var.db_master_password_parameter_name : "/${var.project_name}/db/master/password"
  db_app_password_parameter_name    = var.db_app_password_parameter_name != "" ? var.db_app_password_parameter_name : "/${var.project_name}/db/app/password"

  db_master_password_parameter_selector = var.db_master_password_parameter_version > 0 ? "${local.db_master_password_parameter_name}:${var.db_master_password_parameter_version}" : local.db_master_password_parameter_name
  db_app_password_parameter_resource    = trim(local.db_app_password_parameter_name, "/")
  app_ssm_parameter_prefix_resource     = trim(var.app_ssm_parameter_prefix, "/")

  db_app_password_parameter_arn = "arn:${data.aws_partition.current.partition}:ssm:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:parameter/${local.db_app_password_parameter_resource}"
  app_ssm_parameter_prefix_arn  = "arn:${data.aws_partition.current.partition}:ssm:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:parameter/${local.app_ssm_parameter_prefix_resource}/*"

  db_url = "jdbc:mysql://${aws_db_instance.database.address}:${aws_db_instance.database.port}/${var.db_name}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo"

  dashboard_image_uri = "${aws_ecr_repository.dashboard.repository_url}:${var.image_tag}"
  api_image_uri       = "${aws_ecr_repository.api.repository_url}:${var.image_tag}"
  root_api_image_uri  = "${aws_ecr_repository.root_api.repository_url}:${var.image_tag}"

  api_internal_base_url         = "http://${var.api_service_discovery_name}.${local.service_discovery_namespace_name}/api/"
  root_api_internal_base_url    = "http://${var.root_api_service_discovery_name}.${local.service_discovery_namespace_name}/api/root/"
  dashboard_health_info_api_url = var.health_info_api_url != "" ? var.health_info_api_url : local.api_internal_base_url
  effective_root_api_url        = var.root_api_url != "" ? var.root_api_url : local.root_api_internal_base_url

  dashboard_environment = concat(
    [
      { name = "SPRING_PROFILES_ACTIVE", value = "dev" },
      { name = "SPRING_FLYWAY_ENABLED", value = "true" },
      { name = "SPRING_FLYWAY_LOCATIONS", value = "classpath:/db/migration" },
      { name = "DB_URL", value = local.db_url },
      { name = "DB_USER", value = var.db_app_username },
      { name = "SERVER_PORT", value = tostring(var.dashboard_container_port) },
      { name = "API_LOG_QUEUE_NAME", value = aws_sqs_queue.api_log.name },
      { name = "HEALTHINFO_API_URL", value = local.dashboard_health_info_api_url },
      { name = "ROOT_API_URL", value = local.effective_root_api_url },
    ],
    var.health_info_dashboard_url != "" ? [{ name = "HEALTHINFO_DASHBOARD_URL", value = var.health_info_dashboard_url }] : [],
    var.health_info_track_api_url != "" ? [{ name = "HEALTHINFO_TRACK_API_URL", value = var.health_info_track_api_url }] : [],
  )

  api_environment = concat(
    [
      { name = "SPRING_PROFILES_ACTIVE", value = "dev" },
      { name = "SPRING_FLYWAY_ENABLED", value = "false" },
      { name = "DB_URL", value = local.db_url },
      { name = "DB_USER", value = var.db_app_username },
      { name = "SERVER_PORT", value = tostring(var.api_container_port) },
      { name = "API_LOG_QUEUE_NAME", value = aws_sqs_queue.api_log.name },
      { name = "ROOT_API_URL", value = local.effective_root_api_url },
    ],
    var.health_info_dashboard_url != "" ? [{ name = "HEALTHINFO_DASHBOARD_URL", value = var.health_info_dashboard_url }] : [],
    var.health_info_api_url != "" ? [{ name = "HEALTHINFO_API_URL", value = var.health_info_api_url }] : [],
    var.health_info_track_api_url != "" ? [{ name = "HEALTHINFO_TRACK_API_URL", value = var.health_info_track_api_url }] : [],
  )

  root_api_environment = concat(
    [
      { name = "SPRING_PROFILES_ACTIVE", value = "dev" },
      { name = "SPRING_FLYWAY_ENABLED", value = "false" },
      { name = "DB_URL", value = local.db_url },
      { name = "DB_USER", value = var.db_app_username },
      { name = "SERVER_PORT", value = tostring(var.root_api_container_port) },
      { name = "API_LOG_QUEUE_NAME", value = aws_sqs_queue.api_log.name },
      { name = "FRONT_URL", value = var.root_front_url },
      { name = "AWS_S3_BACKET", value = var.app_data_bucket_name },
    ],
    var.health_info_dashboard_url != "" ? [{ name = "HEALTHINFO_DASHBOARD_URL", value = var.health_info_dashboard_url }] : [],
    var.health_info_api_url != "" ? [{ name = "HEALTHINFO_API_URL", value = var.health_info_api_url }] : [],
    var.health_info_track_api_url != "" ? [{ name = "HEALTHINFO_TRACK_API_URL", value = var.health_info_track_api_url }] : [],
  )

  common_tags = {
    Project   = var.project_name
    ManagedBy = "terraform"
  }
}
