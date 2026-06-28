output "vpc_id" {
  value = module.healthinfo.vpc_id
}

output "cluster_name" {
  value = module.healthinfo.cluster_name
}

output "resource_prefix" {
  value = module.healthinfo.resource_prefix
}

output "service_discovery_namespace_name" {
  value = module.healthinfo.service_discovery_namespace_name
}

output "shared_db_client_security_group_id" {
  value = module.healthinfo.shared_db_client_security_group_id
}

output "internal_app_client_security_group_id" {
  value = module.healthinfo.internal_app_client_security_group_id
}

output "bastion_instance_id" {
  value = module.healthinfo.bastion_instance_id
}

output "bastion_public_ip" {
  value = module.healthinfo.bastion_public_ip
}

output "bastion_security_group_id" {
  value = module.healthinfo.bastion_security_group_id
}

output "bastion_ssm_command" {
  value = module.healthinfo.bastion_ssm_command
}

output "bastion_ssh_command" {
  value = module.healthinfo.bastion_ssh_command
}

output "db_endpoint_address" {
  value = module.healthinfo.db_endpoint_address
}

output "db_port" {
  value = module.healthinfo.db_port
}

output "db_name" {
  value = module.healthinfo.db_name
}

output "db_engine_version" {
  value = module.healthinfo.db_engine_version
}

output "db_master_username" {
  value = module.healthinfo.db_master_username
}

output "db_master_password_parameter_name" {
  value = module.healthinfo.db_master_password_parameter_name
}

output "db_app_username" {
  value = module.healthinfo.db_app_username
}

output "db_app_password_parameter_name" {
  value = module.healthinfo.db_app_password_parameter_name
}

output "app_ssm_parameter_prefix" {
  value = module.healthinfo.app_ssm_parameter_prefix
}

output "api_log_queue_name" {
  value = module.healthinfo.api_log_queue_name
}

output "api_log_queue_url" {
  value = module.healthinfo.api_log_queue_url
}

output "app_env" {
  value = module.healthinfo.app_env
}

output "health_info_dynamodb_table_name" {
  value = module.healthinfo.health_info_dynamodb_table_name
}

output "health_info_dynamodb_table_arn" {
  value = module.healthinfo.health_info_dynamodb_table_arn
}

output "track_django_secret_key_parameter_name" {
  value = module.healthinfo.track_django_secret_key_parameter_name
}

output "secret_storage_policy" {
  value = module.healthinfo.secret_storage_policy
}

output "dashboard_ecr_repository_url" {
  value = module.healthinfo.dashboard_ecr_repository_url
}

output "api_ecr_repository_url" {
  value = module.healthinfo.api_ecr_repository_url
}

output "root_api_ecr_repository_url" {
  value = module.healthinfo.root_api_ecr_repository_url
}

output "track_ecr_repository_url" {
  value = module.healthinfo.track_ecr_repository_url
}

output "batch_ecr_repository_url" {
  value = module.healthinfo.batch_ecr_repository_url
}

output "dashboard_service_name" {
  value = module.healthinfo.dashboard_service_name
}

output "dashboard_health_info_api_url" {
  value = module.healthinfo.dashboard_health_info_api_url
}

output "dashboard_container_port" {
  value = module.healthinfo.dashboard_container_port
}

output "dashboard_desired_count" {
  value = module.healthinfo.dashboard_desired_count
}

output "dashboard_start_command" {
  value = module.healthinfo.dashboard_start_command
}

output "api_service_name" {
  value = module.healthinfo.api_service_name
}

output "api_internal_base_url" {
  value = module.healthinfo.api_internal_base_url
}

output "api_container_port" {
  value = module.healthinfo.api_container_port
}

output "api_desired_count" {
  value = module.healthinfo.api_desired_count
}

output "api_public_allowed_cidr" {
  value = module.healthinfo.api_public_allowed_cidr
}

output "api_start_command" {
  value = module.healthinfo.api_start_command
}

output "track_service_name" {
  value = module.healthinfo.track_service_name
}

output "track_internal_base_url" {
  value = module.healthinfo.track_internal_base_url
}

output "track_container_port" {
  value = module.healthinfo.track_container_port
}

output "track_desired_count" {
  value = module.healthinfo.track_desired_count
}

output "track_public_allowed_cidr" {
  value = module.healthinfo.track_public_allowed_cidr
}

output "track_start_command" {
  value = module.healthinfo.track_start_command
}

output "track_public_url_command" {
  value = module.healthinfo.track_public_url_command
}

output "root_api_service_name" {
  value = module.healthinfo.root_api_service_name
}

output "root_api_internal_base_url" {
  value = module.healthinfo.root_api_internal_base_url
}

output "root_api_container_port" {
  value = module.healthinfo.root_api_container_port
}

output "root_api_desired_count" {
  value = module.healthinfo.root_api_desired_count
}

output "root_api_public_allowed_cidr" {
  value = module.healthinfo.root_api_public_allowed_cidr
}

output "root_api_start_command" {
  value = module.healthinfo.root_api_start_command
}

output "root_api_public_url_command" {
  value = module.healthinfo.root_api_public_url_command
}

output "root_front_url" {
  value = module.healthinfo.root_front_url
}

output "batch_task_definition_arn" {
  value = module.healthinfo.batch_task_definition_arn
}

output "batch_log_group_name" {
  value = module.healthinfo.batch_log_group_name
}

output "batch_run_command" {
  value = module.healthinfo.batch_run_command
}

output "db_create_database_command" {
  value = module.healthinfo.db_create_database_command
}

output "db_connect_command" {
  value = module.healthinfo.db_connect_command
}
