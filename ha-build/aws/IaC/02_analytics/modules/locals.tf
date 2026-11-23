data "aws_caller_identity" "current" {}
data "aws_partition" "current" {}
data "aws_region" "current" {}

locals {
  resource_prefix      = "${var.project_name}-${var.app_env}"
  state_machine_name   = "${local.resource_prefix}-healthinfo-analysis"
  source_bucket_arn    = "arn:${data.aws_partition.current.partition}:s3:::${var.source_bucket_name}"
  input_prefix         = var.source_data_prefix
  athena_result_prefix = var.athena_result_prefix
  glue_database_name   = "healthinfo_${var.app_env}"
  glue_table_name      = "health_info"
  athena_result_s3_uri = "s3://${var.source_bucket_name}/${local.athena_result_prefix}"
}
