# Starts the analysis workflow when a monthly CSV.gz object is created.
resource "aws_cloudwatch_event_rule" "healthinfo_object_created" {
  name        = "${local.resource_prefix}-object-created"
  description = "Starts health information analysis for monthly CSV.gz uploads."
  state       = "ENABLED"

  event_pattern = jsonencode({
    source        = ["aws.s3"]
    account       = [data.aws_caller_identity.current.account_id]
    "detail-type" = ["Object Created"]
    detail = {
      bucket = {
        name = [aws_s3_bucket.app_data.id]
      }
      object = {
        key = [
          {
            wildcard = local.input_key_pattern
          }
        ]
      }
    }
  })
}

resource "aws_cloudwatch_event_target" "step_functions" {
  rule      = aws_cloudwatch_event_rule.healthinfo_object_created.name
  target_id = "HealthInfoAnalysisStateMachine"
  arn       = aws_sfn_state_machine.healthinfo_analysis.arn
  role_arn  = aws_iam_role.eventbridge_role.arn

  dead_letter_config {
    arn = aws_sqs_queue.eventbridge_dlq.arn
  }

  retry_policy {
    maximum_event_age_in_seconds = 3600
    maximum_retry_attempts       = 3
  }

  depends_on = [
    aws_iam_role_policy.eventbridge_policy,
    aws_sqs_queue_policy.eventbridge_dlq,
  ]
}
