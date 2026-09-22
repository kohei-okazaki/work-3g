resource "aws_athena_workgroup" "healthinfo" {
  name          = "${local.resource_prefix}-athena-workgroup"
  description   = "Cost-limited workgroup for monthly health information analysis."
  state         = "ENABLED"
  force_destroy = true

  configuration {
    bytes_scanned_cutoff_per_query     = var.athena_bytes_scanned_cutoff_per_query
    enforce_workgroup_configuration    = true
    publish_cloudwatch_metrics_enabled = false

    engine_version {
      selected_engine_version = "Athena engine version 3"
    }

    result_configuration {
      expected_bucket_owner = data.aws_caller_identity.current.account_id
      output_location       = local.athena_result_s3_uri

      encryption_configuration {
        encryption_option = "SSE_S3"
      }
    }
  }
}
