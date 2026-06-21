output "vpc_id" {
  value = aws_vpc.main.id
}

output "cluster_name" {
  value = aws_ecs_cluster.main.name
}

output "service_discovery_namespace_name" {
  value = local.service_discovery_namespace_name
}

output "shared_db_client_security_group_id" {
  value = aws_security_group.shared_db_client.id
}

output "internal_app_client_security_group_id" {
  value = aws_security_group.internal_app_client.id
}

output "bastion_instance_id" {
  value = aws_instance.bastion.id
}

output "bastion_public_ip" {
  value = aws_instance.bastion.public_ip
}

output "bastion_security_group_id" {
  value = aws_security_group.bastion.id
}

output "bastion_ssm_command" {
  value = "aws ssm start-session --target ${aws_instance.bastion.id}"
}

output "bastion_ssh_command" {
  value = "ssh ec2-user@${aws_instance.bastion.public_ip}"
}

output "db_endpoint_address" {
  value = aws_db_instance.database.address
}

output "db_port" {
  value = aws_db_instance.database.port
}

output "db_name" {
  value = var.db_name
}

output "db_engine_version" {
  value = var.db_engine_version
}

output "db_master_username" {
  value = var.db_master_username
}

output "db_master_password_parameter_name" {
  value = local.db_master_password_parameter_name
}

output "db_app_username" {
  value = var.db_app_username
}

output "db_app_password_parameter_name" {
  value = local.db_app_password_parameter_name
}

output "app_ssm_parameter_prefix" {
  value = var.app_ssm_parameter_prefix
}

output "api_log_queue_name" {
  value = aws_sqs_queue.api_log.name
}

output "api_log_queue_url" {
  value = aws_sqs_queue.api_log.url
}

output "app_env" {
  value = var.app_env
}

output "health_info_dynamodb_table_name" {
  value = aws_dynamodb_table.health_info.name
}

output "health_info_dynamodb_table_arn" {
  value = aws_dynamodb_table.health_info.arn
}

output "track_django_secret_key_parameter_name" {
  value = local.track_django_secret_key_parameter_name
}

output "secret_storage_policy" {
  value = "Use existing SSM Parameter Store Standard SecureString parameters; no Secrets Manager secret is created. Terraform state still contains the RDS master password value read from SSM."
}

output "dashboard_ecr_repository_url" {
  value = aws_ecr_repository.dashboard.repository_url
}

output "api_ecr_repository_url" {
  value = aws_ecr_repository.api.repository_url
}

output "root_api_ecr_repository_url" {
  value = aws_ecr_repository.root_api.repository_url
}

output "track_ecr_repository_url" {
  value = aws_ecr_repository.track.repository_url
}

output "batch_ecr_repository_url" {
  value = aws_ecr_repository.batch.repository_url
}

output "dashboard_service_name" {
  value = aws_ecs_service.dashboard.name
}

output "dashboard_health_info_api_url" {
  value = local.dashboard_health_info_api_url
}

output "dashboard_container_port" {
  value = var.dashboard_container_port
}

output "dashboard_desired_count" {
  value = var.dashboard_desired_count
}

output "dashboard_start_command" {
  value = "aws ecs update-service --cluster ${aws_ecs_cluster.main.name} --service ${aws_ecs_service.dashboard.name} --desired-count 1"
}

output "api_service_name" {
  value = aws_ecs_service.api.name
}

output "api_internal_base_url" {
  value = local.api_internal_base_url
}

output "api_container_port" {
  value = var.api_container_port
}

output "api_desired_count" {
  value = var.api_desired_count
}

output "api_public_allowed_cidr" {
  value = var.api_public_allowed_cidr != "" ? var.api_public_allowed_cidr : "disabled"
}

output "api_start_command" {
  value = "aws ecs update-service --cluster ${aws_ecs_cluster.main.name} --service ${aws_ecs_service.api.name} --desired-count 1"
}

output "track_service_name" {
  value = aws_ecs_service.track.name
}

output "track_internal_base_url" {
  value = local.track_internal_base_url
}

output "track_container_port" {
  value = var.track_container_port
}

output "track_desired_count" {
  value = var.track_desired_count
}

output "track_public_allowed_cidr" {
  value = var.track_public_allowed_cidr != "" ? var.track_public_allowed_cidr : "disabled"
}

output "track_start_command" {
  value = "aws ecs update-service --cluster ${aws_ecs_cluster.main.name} --service ${aws_ecs_service.track.name} --desired-count 1"
}

output "track_public_url_command" {
  value = "CLUSTER_NAME=${aws_ecs_cluster.main.name} && SERVICE_NAME=${aws_ecs_service.track.name} && TASK_ARN=$(aws ecs list-tasks --cluster \"$CLUSTER_NAME\" --service-name \"$SERVICE_NAME\" --query 'taskArns[0]' --output text) && ENI_ID=$(aws ecs describe-tasks --cluster \"$CLUSTER_NAME\" --tasks \"$TASK_ARN\" --query 'tasks[0].attachments[0].details[?name==`networkInterfaceId`].value' --output text) && PUBLIC_IP=$(aws ec2 describe-network-interfaces --network-interface-ids \"$ENI_ID\" --query 'NetworkInterfaces[0].Association.PublicIp' --output text) && echo \"http://$${PUBLIC_IP}:${var.track_container_port}/api/\""
}

output "root_api_service_name" {
  value = aws_ecs_service.root_api.name
}

output "root_api_internal_base_url" {
  value = local.root_api_internal_base_url
}

output "root_api_container_port" {
  value = var.root_api_container_port
}

output "root_api_desired_count" {
  value = var.root_api_desired_count
}

output "root_api_public_allowed_cidr" {
  value = var.root_api_public_allowed_cidr != "" ? var.root_api_public_allowed_cidr : "disabled"
}

output "root_api_start_command" {
  value = "aws ecs update-service --cluster ${aws_ecs_cluster.main.name} --service ${aws_ecs_service.root_api.name} --desired-count 1"
}

output "root_api_public_url_command" {
  value = "CLUSTER_NAME=${aws_ecs_cluster.main.name} && SERVICE_NAME=${aws_ecs_service.root_api.name} && TASK_ARN=$(aws ecs list-tasks --cluster \"$CLUSTER_NAME\" --service-name \"$SERVICE_NAME\" --query 'taskArns[0]' --output text) && ENI_ID=$(aws ecs describe-tasks --cluster \"$CLUSTER_NAME\" --tasks \"$TASK_ARN\" --query 'tasks[0].attachments[0].details[?name==`networkInterfaceId`].value' --output text) && PUBLIC_IP=$(aws ec2 describe-network-interfaces --network-interface-ids \"$ENI_ID\" --query 'NetworkInterfaces[0].Association.PublicIp' --output text) && echo \"http://$${PUBLIC_IP}/api/root/\""
}

output "root_front_url" {
  value = var.root_front_url
}

output "batch_task_definition_arn" {
  value = aws_ecs_task_definition.batch.arn
}

output "batch_log_group_name" {
  value = aws_cloudwatch_log_group.batch.name
}

output "batch_run_command" {
  value = "JOB_NAME=healthCheckBatchJob; RUN_ID=$(date +%Y%m%d%H%M%S%N); aws ecs run-task --cluster ${aws_ecs_cluster.main.name} --launch-type FARGATE --task-definition ${aws_ecs_task_definition.batch.arn} --network-configuration 'awsvpcConfiguration={subnets=[${join(",", aws_subnet.public[*].id)}],securityGroups=[${aws_security_group.shared_db_client.id},${aws_security_group.internal_app_client.id}],assignPublicIp=ENABLED}' --overrides \"$(printf '{\\\"containerOverrides\\\":[{\\\"name\\\":\\\"BatchContainer\\\",\\\"command\\\":[\\\"--spring.batch.job.name=%s\\\",\\\"run.id=%s\\\"]}]}' \"$JOB_NAME\" \"$RUN_ID\")\""
}

output "db_create_database_command" {
  value = "mysql -h ${aws_db_instance.database.address} -P ${aws_db_instance.database.port} -u ${var.db_master_username} -p < CREATE_DATABASE.sql"
}

output "db_connect_command" {
  value = "mysql -h ${aws_db_instance.database.address} -P ${aws_db_instance.database.port} -u ${var.db_master_username} -p"
}
