output "vpc_id" {
  value = module.application.vpc_id
}

output "cluster_name" {
  value = module.application.cluster_name
}

output "resource_prefix" {
  value = module.application.resource_prefix
}

output "service_discovery_namespace_name" {
  value = module.application.service_discovery_namespace_name
}

output "shared_db_client_security_group_id" {
  value = module.application.shared_db_client_security_group_id
}

output "internal_app_client_security_group_id" {
  value = module.application.internal_app_client_security_group_id
}

output "bastion_instance_id" {
  value = module.application.bastion_instance_id
}

output "bastion_public_ip" {
  value = module.application.bastion_public_ip
}

output "bastion_security_group_id" {
  value = module.application.bastion_security_group_id
}

output "bastion_ssm_command" {
  value = module.application.bastion_ssm_command
}

output "bastion_ssh_command" {
  value = module.application.bastion_ssh_command
}

output "db_endpoint_address" {
  value = module.application.db_endpoint_address
}

output "db_port" {
  value = module.application.db_port
}

output "db_name" {
  value = module.application.db_name
}

output "db_engine_version" {
  value = module.application.db_engine_version
}

output "db_master_username" {
  value = module.application.db_master_username
}

output "db_master_password_parameter_name" {
  value = module.application.db_master_password_parameter_name
}

output "db_app_username" {
  value = module.application.db_app_username
}

output "db_app_password_parameter_name" {
  value = module.application.db_app_password_parameter_name
}

output "app_ssm_parameter_prefix" {
  value = module.application.app_ssm_parameter_prefix
}

output "api_log_queue_name" {
  value = module.application.api_log_queue_name
}

output "api_log_queue_url" {
  value = module.application.api_log_queue_url
}

output "app_env" {
  value = module.application.app_env
}

output "app_data_bucket_name" {
  value = module.application.app_data_bucket_name
}

output "app_data_bucket_arn" {
  value = module.application.app_data_bucket_arn
}

output "health_info_dynamodb_table_name" {
  value = module.application.health_info_dynamodb_table_name
}

output "health_info_dynamodb_table_arn" {
  value = module.application.health_info_dynamodb_table_arn
}

output "track_django_secret_key_parameter_name" {
  value = module.application.track_django_secret_key_parameter_name
}

output "secret_storage_policy" {
  value = module.application.secret_storage_policy
}

output "dashboard_ecr_repository_url" {
  value = module.application.dashboard_ecr_repository_url
}

output "api_ecr_repository_url" {
  value = module.application.api_ecr_repository_url
}

output "root_api_ecr_repository_url" {
  value = module.application.root_api_ecr_repository_url
}

output "track_ecr_repository_url" {
  value = module.application.track_ecr_repository_url
}

output "batch_ecr_repository_url" {
  value = module.application.batch_ecr_repository_url
}

output "dashboard_service_name" {
  value = module.application.dashboard_service_name
}

output "dashboard_health_info_api_url" {
  value = module.application.dashboard_health_info_api_url
}

output "dashboard_container_port" {
  value = module.application.dashboard_container_port
}

output "dashboard_desired_count" {
  value = module.application.dashboard_desired_count
}

output "dashboard_start_command" {
  value = module.application.dashboard_start_command
}

output "api_service_name" {
  value = module.application.api_service_name
}

output "api_internal_base_url" {
  value = module.application.api_internal_base_url
}

output "api_container_port" {
  value = module.application.api_container_port
}

output "api_desired_count" {
  value = module.application.api_desired_count
}

output "api_public_allowed_cidr" {
  value = module.application.api_public_allowed_cidr
}

output "api_start_command" {
  value = module.application.api_start_command
}

output "track_service_name" {
  value = module.application.track_service_name
}

output "track_internal_base_url" {
  value = module.application.track_internal_base_url
}

output "track_container_port" {
  value = module.application.track_container_port
}

output "track_desired_count" {
  value = module.application.track_desired_count
}

output "track_public_allowed_cidr" {
  value = module.application.track_public_allowed_cidr
}

output "track_start_command" {
  value = module.application.track_start_command
}

output "track_public_url_command" {
  value = module.application.track_public_url_command
}

output "root_api_service_name" {
  value = module.application.root_api_service_name
}

output "root_api_internal_base_url" {
  value = module.application.root_api_internal_base_url
}

output "root_api_container_port" {
  value = module.application.root_api_container_port
}

output "root_api_desired_count" {
  value = module.application.root_api_desired_count
}

output "root_api_public_allowed_cidr" {
  value = module.application.root_api_public_allowed_cidr
}

output "root_api_start_command" {
  value = module.application.root_api_start_command
}

output "root_api_public_url_command" {
  value = module.application.root_api_public_url_command
}

output "root_front_url" {
  value = module.application.root_front_url
}

output "batch_task_definition_arn" {
  value = module.application.batch_task_definition_arn
}

output "batch_log_group_name" {
  value = module.application.batch_log_group_name
}

output "batch_run_command" {
  value = module.application.batch_run_command
}

output "db_create_database_command" {
  value = module.application.db_create_database_command
}

output "db_connect_command" {
  value = module.application.db_connect_command
}

output "analytics_inputs" {
  description = "分析基盤へ渡す非機密の接続情報"
  value       = module.application.analytics_inputs
}
