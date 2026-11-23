resource "aws_cloudwatch_log_group" "step_functions" {
  name              = "/aws/vendedlogs/states/${local.state_machine_name}"
  retention_in_days = var.step_functions_log_retention_days
}
