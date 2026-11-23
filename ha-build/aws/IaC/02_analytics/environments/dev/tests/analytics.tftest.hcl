mock_provider "aws" {
  mock_data "aws_caller_identity" {
    defaults = { account_id = "123456789012" }
  }
  mock_data "aws_partition" {
    defaults = { partition = "aws" }
  }
  mock_data "aws_region" {
    defaults = { region = "ap-northeast-1" }
  }
  mock_resource "aws_cloudwatch_log_group" {
    defaults = { arn = "arn:aws:logs:ap-northeast-1:123456789012:log-group:analytics" }
  }
  mock_resource "aws_sfn_state_machine" {
    defaults = { arn = "arn:aws:states:ap-northeast-1:123456789012:stateMachine:analytics" }
  }
  mock_resource "aws_sns_topic" {
    defaults = { arn = "arn:aws:sns:ap-northeast-1:123456789012:analytics" }
  }
  mock_resource "aws_sqs_queue" {
    defaults = { arn = "arn:aws:sqs:ap-northeast-1:123456789012:event-dlq" }
  }
}

run "analytics_uses_only_supplied_data_location" {
  command = apply

  module {
    source = "../../modules"
  }

  variables {
    project_name         = "analytics-test"
    app_env              = "dev"
    source_bucket_name   = "existing-app-data-fixture"
    source_data_prefix   = "exports/health/"
    athena_result_prefix = "queries/results/"
    slack_team_id        = "T0123456789"
    slack_channel_id     = "C0123456789"
  }

  override_resource {
    target = aws_iam_role.step_functions_role
    values = { arn = "arn:aws:iam::123456789012:role/analysis-workflow" }
  }
  override_resource {
    target = aws_iam_role.eventbridge_role
    values = { arn = "arn:aws:iam::123456789012:role/analysis-eventbridge" }
  }
  override_resource {
    target = aws_iam_role.amazon_q_role
    values = { arn = "arn:aws:iam::123456789012:role/analysis-notifications" }
  }

  assert {
    condition = (
      aws_glue_catalog_table.health_info.storage_descriptor[0].location == "s3://existing-app-data-fixture/exports/health/" &&
      aws_glue_catalog_table.health_info.parameters["storage.location.template"] == "s3://existing-app-data-fixture/exports/health/year=$${year}/" &&
      output.healthinfo_analysis_athena_result_s3_uri == "s3://existing-app-data-fixture/queries/results/"
    )
    error_message = "Glue and Athena must use the supplied bucket and prefixes rather than app naming conventions."
  }

  assert {
    condition = (
      jsondecode(aws_cloudwatch_event_rule.healthinfo_object_created.event_pattern).detail.bucket.name[0] == "existing-app-data-fixture" &&
      jsondecode(aws_cloudwatch_event_rule.healthinfo_object_created.event_pattern).detail.object.key[0].wildcard == "exports/health/year=*/*.csv.gz"
    )
    error_message = "EventBridge must filter only the supplied application data location."
  }

  assert {
    condition = (
      aws_sfn_state_machine.healthinfo_analysis.role_arn == aws_iam_role.step_functions_role.arn &&
      aws_cloudwatch_event_target.step_functions.role_arn == aws_iam_role.eventbridge_role.arn &&
      aws_chatbot_slack_channel_configuration.healthinfo_analysis.iam_role_arn == aws_iam_role.amazon_q_role.arn
    )
    error_message = "Analytics services must use their own analytics IAM roles."
  }

  assert {
    condition = !contains(flatten([
      for statement in jsondecode(aws_iam_role_policy.step_functions_policy.policy).Statement :
      try(tolist(statement.Action), [statement.Action])
    ]), "lakeformation:GetDataAccess")
    error_message = "The IAM-only analytics workflow must not request Lake Formation data access."
  }

  assert {
    condition = (
      one([for statement in jsondecode(aws_iam_role_policy.step_functions_policy.policy).Statement : statement.Resource if statement.Sid == "WriteAthenaResults"]) == "arn:aws:s3:::existing-app-data-fixture/queries/results/*" &&
      toset(one([for statement in jsondecode(aws_iam_role_policy.step_functions_policy.policy).Statement : statement.Resource if statement.Sid == "ReadMonthlyHealthInfo"])) == toset([
        "arn:aws:s3:::existing-app-data-fixture/exports/health/*",
        "arn:aws:s3:::existing-app-data-fixture/queries/results/*",
      ])
    )
    error_message = "Analytics may read the source data and results, and may only write to the result prefix."
  }
}
