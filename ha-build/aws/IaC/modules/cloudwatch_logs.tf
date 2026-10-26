resource "aws_cloudwatch_log_group" "dashboard" {
  name              = "/ecs/${local.resource_prefix}/ha-dashboard"
  retention_in_days = 1
}

resource "aws_cloudwatch_log_group" "api" {
  name              = "/ecs/${local.resource_prefix}/ha-api"
  retention_in_days = 1
}

resource "aws_cloudwatch_log_group" "root_api" {
  name              = "/ecs/${local.resource_prefix}/ha-root-api"
  retention_in_days = 1
}

resource "aws_cloudwatch_log_group" "track" {
  name              = "/ecs/${local.resource_prefix}/ha-track"
  retention_in_days = 1
}

resource "aws_cloudwatch_log_group" "batch" {
  name              = "/ecs/${local.resource_prefix}/ha-batch"
  retention_in_days = 1
}

resource "aws_cloudwatch_log_group" "step_functions" {
  name              = "/aws/vendedlogs/states/${local.state_machine_name}"
  retention_in_days = var.step_functions_log_retention_days
}
