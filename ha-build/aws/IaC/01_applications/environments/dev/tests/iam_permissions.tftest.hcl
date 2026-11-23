# AWS calls and resource creation are mocked; the deployment state is not used.
mock_provider "aws" {
  mock_data "aws_iam_policy_document" {
    defaults = {
      json = jsonencode({ Version = "2012-10-17", Statement = [] })
    }
  }

  mock_data "aws_availability_zones" {
    defaults = {
      names = ["ap-northeast-1a", "ap-northeast-1c"]
    }
  }

  mock_data "aws_caller_identity" {
    defaults = {
      account_id = "123456789012"
    }
  }

  mock_data "aws_partition" {
    defaults = {
      partition = "aws"
    }
  }

  mock_data "aws_region" {
    defaults = {
      region = "ap-northeast-1"
    }
  }

  mock_resource "aws_iam_role" {
    defaults = {
      arn = "arn:aws:iam::123456789012:role/mock-role"
    }
  }

  mock_resource "aws_cloudwatch_log_group" {
    defaults = {
      arn = "arn:aws:logs:ap-northeast-1:123456789012:log-group:mock-step-functions"
    }
  }

  mock_resource "aws_sfn_state_machine" {
    defaults = {
      arn = "arn:aws:states:ap-northeast-1:123456789012:stateMachine:mock-analysis"
    }
  }

  mock_resource "aws_sns_topic" {
    defaults = {
      arn = "arn:aws:sns:ap-northeast-1:123456789012:mock-notifications"
    }
  }

  mock_resource "aws_sqs_queue" {
    defaults = {
      arn = "arn:aws:sqs:ap-northeast-1:123456789012:mock-queue"
    }
  }

  mock_resource "aws_service_discovery_service" {
    defaults = {
      arn = "arn:aws:servicediscovery:ap-northeast-1:123456789012:service/srv-mock"
    }
  }
}

run "permissions_match_workloads" {
  command = apply

  module {
    source = "../../modules"
  }

  # Distinct ARNs make cross-task log access detectable by the assertions.
  override_resource {
    target = aws_cloudwatch_log_group.dashboard
    values = { arn = "arn:aws:logs:ap-northeast-1:123456789012:log-group:dashboard" }
  }

  override_resource {
    target = aws_cloudwatch_log_group.api
    values = { arn = "arn:aws:logs:ap-northeast-1:123456789012:log-group:api" }
  }

  override_resource {
    target = aws_cloudwatch_log_group.root_api
    values = { arn = "arn:aws:logs:ap-northeast-1:123456789012:log-group:root-api" }
  }

  override_resource {
    target = aws_cloudwatch_log_group.track
    values = { arn = "arn:aws:logs:ap-northeast-1:123456789012:log-group:track" }
  }

  override_resource {
    target = aws_cloudwatch_log_group.batch
    values = { arn = "arn:aws:logs:ap-northeast-1:123456789012:log-group:batch" }
  }

  variables {
    project_name             = "iam-test"
    app_env                  = "dev"
    app_ssm_parameter_prefix = "/dev"
  }

  assert {
    # ECS fetches injected secrets with GetParameters; application code uses GetParameter.
    condition = (
      toset(flatten([
        for statement in data.aws_iam_policy_document.ecs_execution_ssm.statement :
        [for action in statement.actions : action if startswith(action, "ssm:")]
      ])) == toset(["ssm:GetParameters"]) &&
      toset(flatten([
        for statement in data.aws_iam_policy_document.app_task_policy.statement :
        [for action in statement.actions : action if startswith(action, "ssm:")]
      ])) == toset(["ssm:GetParameter"]) &&
      toset(jsondecode(aws_iam_role_policy.track_execution_ssm_policy.policy).Statement[0].Action) == toset(["ssm:GetParameters"])
    )
    error_message = "Execution and application roles must only allow their respective SSM retrieval API."
  }

  assert {
    condition = toset(flatten([
      for statement in data.aws_iam_policy_document.app_task_policy.statement :
      tolist(statement.resources) if contains(statement.actions, "sqs:GetQueueUrl")
    ])) == toset([aws_sqs_queue.api_log.arn])
    error_message = "Application roles must only resolve the API-log queue URL."
  }

  assert {
    # Batch jobs upload CSVs, consume API logs and send health-check mail.
    condition = toset(flatten([
      for statement in jsondecode(aws_iam_role_policy.batch_task_aws_access_policy.policy).Statement : statement.Action
      ])) == toset([
      "sqs:DeleteMessage", "sqs:ReceiveMessage",
      "s3:PutObject", "s3:PutObjectAcl", "ses:SendEmail",
    ])
    error_message = "Batch roles must retain uploads, queue consumption and mail without unused read/delete/verification APIs."
  }

  assert {
    condition = toset(flatten([
      for statement in jsondecode(aws_iam_role_policy.batch_task_aws_access_policy.policy).Statement :
      statement.Resource if contains(statement.Action, "s3:PutObject")
      ])) == toset([
      "${aws_s3_bucket.app_data.arn}/analysis/*",
      "${aws_s3_bucket.app_data.arn}/monthly/healthinfo/*",
    ])
    error_message = "Batch uploads must be limited to daily and monthly export prefixes."
  }

  assert {
    # Root API reads/edits notes and news, deletes news and sends inquiry mail.
    condition = toset(flatten([
      for statement in jsondecode(aws_iam_role_policy.root_api_task_aws_access_policy.policy).Statement : statement.Action
      ])) == toset([
      "s3:GetObject", "s3:PutObject", "s3:PutObjectAcl", "s3:DeleteObject", "ses:SendEmail",
    ])
    error_message = "Root API permissions must preserve object and email operations without bucket listing or unused SES APIs."
  }

  assert {
    condition = alltrue([
      for task, resources in {
        dashboard = { role = aws_iam_role.dashboard_task_execution_role.id, repository = aws_ecr_repository.dashboard.arn, logs = aws_cloudwatch_log_group.dashboard.arn }
        api       = { role = aws_iam_role.api_task_execution_role.id, repository = aws_ecr_repository.api.arn, logs = aws_cloudwatch_log_group.api.arn }
        root_api  = { role = aws_iam_role.root_api_task_execution_role.id, repository = aws_ecr_repository.root_api.arn, logs = aws_cloudwatch_log_group.root_api.arn }
        track     = { role = aws_iam_role.track_task_execution_role.id, repository = aws_ecr_repository.track.arn, logs = aws_cloudwatch_log_group.track.arn }
        batch     = { role = aws_iam_role.batch_task_execution_role.id, repository = aws_ecr_repository.batch.arn, logs = aws_cloudwatch_log_group.batch.arn }
        } : (
        aws_iam_role_policy.ecs_task_execution_policy[task].role == resources.role &&
        length(jsondecode(aws_iam_role_policy.ecs_task_execution_policy[task].policy).Statement) == 3 &&
        jsondecode(aws_iam_role_policy.ecs_task_execution_policy[task].policy).Statement[0].Action == "ecr:GetAuthorizationToken" &&
        jsondecode(aws_iam_role_policy.ecs_task_execution_policy[task].policy).Statement[0].Resource == "*" &&
        toset(jsondecode(aws_iam_role_policy.ecs_task_execution_policy[task].policy).Statement[1].Action) == toset(["ecr:BatchCheckLayerAvailability", "ecr:GetDownloadUrlForLayer", "ecr:BatchGetImage"]) &&
        jsondecode(aws_iam_role_policy.ecs_task_execution_policy[task].policy).Statement[1].Resource == resources.repository &&
        toset(jsondecode(aws_iam_role_policy.ecs_task_execution_policy[task].policy).Statement[2].Action) == toset(["logs:CreateLogStream", "logs:PutLogEvents"]) &&
        jsondecode(aws_iam_role_policy.ecs_task_execution_policy[task].policy).Statement[2].Resource == "${resources.logs}:log-stream:*"
      )
    ])
    error_message = "Each ECS execution role must only pull its own image and write to its own log streams."
  }

  assert {
    condition     = length(aws_security_group.db.egress) == 0 && aws_security_group_rule.db_from_app.from_port == 3306 && aws_security_group_rule.db_from_bastion.from_port == 3306
    error_message = "RDS must allow existing MySQL clients without unrestricted outbound access."
  }

  assert {
    condition = (
      toset(keys(output.analytics_inputs)) == toset(["project_name", "aws_region", "app_env", "source_bucket_name", "source_data_prefix", "athena_result_prefix"]) &&
      output.analytics_inputs.source_bucket_name == aws_s3_bucket.app_data.id &&
      output.analytics_inputs.source_data_prefix == "monthly/healthinfo/" &&
      output.analytics_inputs.athena_result_prefix == "monthly/athena-results/"
    )
    error_message = "The application handoff must contain only the six non-secret values used by analytics."
  }
}

run "analytics_uses_application_outputs" {
  command = apply

  module {
    source = "../../../02_analytics/modules"
  }

  variables {
    project_name         = run.permissions_match_workloads.analytics_inputs.project_name
    app_env              = run.permissions_match_workloads.analytics_inputs.app_env
    source_bucket_name   = run.permissions_match_workloads.analytics_inputs.source_bucket_name
    source_data_prefix   = run.permissions_match_workloads.analytics_inputs.source_data_prefix
    athena_result_prefix = run.permissions_match_workloads.analytics_inputs.athena_result_prefix
    slack_team_id        = "T0123456789"
    slack_channel_id     = "C0123456789"
  }

  assert {
    condition = (
      jsondecode(aws_cloudwatch_event_rule.healthinfo_object_created.event_pattern).detail.bucket.name[0] == run.permissions_match_workloads.analytics_inputs.source_bucket_name &&
      aws_glue_catalog_table.health_info.storage_descriptor[0].location == "s3://${run.permissions_match_workloads.analytics_inputs.source_bucket_name}/monthly/healthinfo/" &&
      output.healthinfo_analysis_athena_result_s3_uri == "s3://${run.permissions_match_workloads.analytics_inputs.source_bucket_name}/monthly/athena-results/"
    )
    error_message = "The independent analytics module must consume the application's exported bucket and paths."
  }
}
