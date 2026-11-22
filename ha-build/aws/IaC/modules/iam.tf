data "aws_iam_policy_document" "ecs_tasks_assume_role" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

data "aws_iam_policy_document" "ecs_execution_ssm" {
  statement {
    actions = [
      "ssm:GetParameter",
      "ssm:GetParameters",
    ]
    resources = [local.db_app_password_parameter_arn]
  }
  statement {
    actions   = ["kms:Decrypt"]
    resources = ["*"]
    condition {
      test     = "StringEquals"
      variable = "kms:ViaService"
      values   = ["ssm.${data.aws_region.current.region}.amazonaws.com"]
    }
  }
}

data "aws_iam_policy_document" "app_task_policy" {
  statement {
    actions = [
      "ssm:GetParameter",
      "ssm:GetParameters",
    ]
    resources = [local.app_ssm_parameter_prefix_arn]
  }
  statement {
    actions   = ["sqs:GetQueueUrl"]
    resources = ["*"]
  }
  statement {
    actions   = ["sqs:SendMessage"]
    resources = [aws_sqs_queue.api_log.arn]
  }
}

resource "aws_iam_role" "dashboard_task_execution_role" {
  name               = "${local.resource_prefix}-dashboard-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role" "dashboard_task_role" {
  name               = "${local.resource_prefix}-dashboard-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role" "api_task_execution_role" {
  name               = "${local.resource_prefix}-api-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role" "api_task_role" {
  name               = "${local.resource_prefix}-api-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role" "root_api_task_execution_role" {
  name               = "${local.resource_prefix}-root-api-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role" "root_api_task_role" {
  name               = "${local.resource_prefix}-root-api-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role" "track_task_execution_role" {
  name               = "${local.resource_prefix}-track-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role" "track_task_role" {
  name               = "${local.resource_prefix}-track-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role" "batch_task_execution_role" {
  name               = "${local.resource_prefix}-batch-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role" "batch_task_role" {
  name               = "${local.resource_prefix}-batch-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
}

resource "aws_iam_role_policy_attachment" "dashboard_task_execution_managed" {
  role       = aws_iam_role.dashboard_task_execution_role.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "api_task_execution_managed" {
  role       = aws_iam_role.api_task_execution_role.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "root_api_task_execution_managed" {
  role       = aws_iam_role.root_api_task_execution_role.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "track_task_execution_managed" {
  role       = aws_iam_role.track_task_execution_role.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "batch_task_execution_managed" {
  role       = aws_iam_role.batch_task_execution_role.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy" "track_execution_ssm_policy" {
  name = "${local.resource_prefix}-track-exec-ssm"
  role = aws_iam_role.track_task_execution_role.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "ssm:GetParameter",
          "ssm:GetParameters",
        ]
        Resource = [
          local.track_django_secret_key_parameter_arn,
        ]
      },
      {
        Effect   = "Allow"
        Action   = "kms:Decrypt"
        Resource = "*"

        Condition = {
          StringEquals = {
            "kms:ViaService" = "ssm.${data.aws_region.current.region}.amazonaws.com"
          }
        }
      }
    ]
  })
}

resource "aws_iam_role_policy" "dashboard_execution_ssm_policy" {
  name   = "${local.resource_prefix}-dashboard-exec-ssm"
  role   = aws_iam_role.dashboard_task_execution_role.id
  policy = data.aws_iam_policy_document.ecs_execution_ssm.json
}

resource "aws_iam_role_policy" "api_execution_ssm_policy" {
  name   = "${local.resource_prefix}-api-exec-ssm"
  role   = aws_iam_role.api_task_execution_role.id
  policy = data.aws_iam_policy_document.ecs_execution_ssm.json
}

resource "aws_iam_role_policy" "root_api_execution_ssm_policy" {
  name   = "${local.resource_prefix}-root-api-exec-ssm"
  role   = aws_iam_role.root_api_task_execution_role.id
  policy = data.aws_iam_policy_document.ecs_execution_ssm.json
}

resource "aws_iam_role_policy" "batch_execution_ssm_policy" {
  name   = "${local.resource_prefix}-batch-exec-ssm"
  role   = aws_iam_role.batch_task_execution_role.id
  policy = data.aws_iam_policy_document.ecs_execution_ssm.json
}

resource "aws_iam_role_policy" "dashboard_task_app_policy" {
  name   = "${local.resource_prefix}-dashboard-task-app"
  role   = aws_iam_role.dashboard_task_role.id
  policy = data.aws_iam_policy_document.app_task_policy.json
}

resource "aws_iam_role_policy" "api_task_app_policy" {
  name   = "${local.resource_prefix}-api-task-app"
  role   = aws_iam_role.api_task_role.id
  policy = data.aws_iam_policy_document.app_task_policy.json
}

resource "aws_iam_role_policy" "root_api_task_app_policy" {
  name   = "${local.resource_prefix}-root-api-task-app"
  role   = aws_iam_role.root_api_task_role.id
  policy = data.aws_iam_policy_document.app_task_policy.json
}

resource "aws_iam_role_policy" "batch_task_app_policy" {
  name   = "${local.resource_prefix}-batch-task-app"
  role   = aws_iam_role.batch_task_role.id
  policy = data.aws_iam_policy_document.app_task_policy.json
}

resource "aws_iam_role_policy" "batch_task_aws_access_policy" {
  name = "${local.resource_prefix}-batch-task-aws-access"
  role = aws_iam_role.batch_task_role.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "sqs:DeleteMessage",
          "sqs:GetQueueAttributes",
          "sqs:ReceiveMessage",
        ]
        Resource = [aws_sqs_queue.api_log.arn]
      },
      {
        Effect = "Allow"
        Action = [
          "s3:ListBucket",
        ]
        Resource = [aws_s3_bucket.app_data.arn]
      },
      {
        Effect = "Allow"
        Action = [
          "s3:DeleteObject",
          "s3:GetObject",
          "s3:PutObject",
          "s3:PutObjectAcl",
        ]
        Resource = ["${aws_s3_bucket.app_data.arn}/*"]
      },
      {
        Effect = "Allow"
        Action = [
          "ses:SendEmail",
          "ses:SendRawEmail",
          "ses:VerifyEmailIdentity",
        ]
        Resource = "*"
      }
    ]
  })
}

resource "aws_iam_role_policy" "root_api_task_aws_access_policy" {
  name = "${local.resource_prefix}-root-api-task-aws-access"
  role = aws_iam_role.root_api_task_role.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "s3:ListBucket",
        ]
        Resource = [aws_s3_bucket.app_data.arn]
      },
      {
        Effect = "Allow"
        Action = [
          "s3:GetObject",
          "s3:PutObject",
          "s3:PutObjectAcl",
          "s3:DeleteObject",
        ]
        Resource = ["${aws_s3_bucket.app_data.arn}/*"]
      },
      {
        Effect = "Allow"
        Action = [
          "ses:SendEmail",
          "ses:SendRawEmail",
          "ses:VerifyEmailIdentity",
        ]
        Resource = "*"
      }
    ]
  })
}

resource "aws_iam_role_policy" "track_task_dynamodb_policy" {
  name = "${local.resource_prefix}-track-task-dynamodb"
  role = aws_iam_role.track_task_role.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "dynamodb:PutItem",
        ]
        Resource = [aws_dynamodb_table.health_info.arn]
      }
    ]
  })
}

resource "aws_iam_role" "bastion_role" {
  name = "${local.resource_prefix}-bastion-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = "sts:AssumeRole"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "bastion_ssm" {
  role       = aws_iam_role.bastion_role.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_instance_profile" "bastion" {
  name = "${local.resource_prefix}-bastion-profile"
  role = aws_iam_role.bastion_role.name
}

# IAM roles and policies for the monthly health information analysis workflow.
resource "aws_iam_role" "step_functions_role" {
  name = "${local.resource_prefix}-sfn-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = "sts:AssumeRole"
        Principal = {
          Service = "states.amazonaws.com"
        }
        Condition = {
          StringEquals = {
            "aws:SourceAccount" = data.aws_caller_identity.current.account_id
          }
          ArnLike = {
            "aws:SourceArn" = "arn:${data.aws_partition.current.partition}:states:${data.aws_region.current.region}:${data.aws_caller_identity.current.account_id}:stateMachine:${local.state_machine_name}"
          }
        }
      }
    ]
  })
}

resource "aws_iam_role_policy" "step_functions_policy" {
  name = "${local.resource_prefix}-sfn-policy"
  role = aws_iam_role.step_functions_role.id
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid    = "RunAthenaQuery"
        Effect = "Allow"
        Action = [
          "athena:BatchGetQueryExecution",
          "athena:GetDataCatalog",
          "athena:GetQueryExecution",
          "athena:GetQueryResults",
          "athena:GetWorkGroup",
          "athena:ListQueryExecutions",
          "athena:StartQueryExecution",
          "athena:StopQueryExecution",
        ]
        Resource = [
          aws_athena_workgroup.healthinfo.arn,
          "arn:${data.aws_partition.current.partition}:athena:${data.aws_region.current.region}:${data.aws_caller_identity.current.account_id}:datacatalog/AwsDataCatalog",
        ]
      },
      {
        Sid    = "ReadGlueCatalog"
        Effect = "Allow"
        Action = [
          "glue:BatchGetPartition",
          "glue:GetDatabase",
          "glue:GetDatabases",
          "glue:GetPartition",
          "glue:GetPartitions",
          "glue:GetTable",
          "glue:GetTables",
        ]
        Resource = [
          "arn:${data.aws_partition.current.partition}:glue:${data.aws_region.current.region}:${data.aws_caller_identity.current.account_id}:catalog",
          aws_glue_catalog_database.healthinfo.arn,
          aws_glue_catalog_table.health_info.arn,
        ]
      },
      {
        Sid      = "ReadLakeFormationData"
        Effect   = "Allow"
        Action   = "lakeformation:GetDataAccess"
        Resource = "*"
      },
      {
        Sid    = "ReadBucketMetadata"
        Effect = "Allow"
        Action = [
          "s3:GetBucketLocation",
          "s3:ListBucketMultipartUploads",
        ]
        Resource = aws_s3_bucket.app_data.arn
      },
      {
        Sid      = "ListInputAndResultPrefixes"
        Effect   = "Allow"
        Action   = "s3:ListBucket"
        Resource = aws_s3_bucket.app_data.arn
        Condition = {
          StringLike = {
            "s3:prefix" = [
              "${local.input_prefix}*",
              "${local.athena_result_prefix}*",
            ]
          }
        }
      },
      {
        Sid    = "ReadMonthlyHealthInfo"
        Effect = "Allow"
        Action = "s3:GetObject"
        Resource = [
          "${aws_s3_bucket.app_data.arn}/${local.input_prefix}*",
          "${aws_s3_bucket.app_data.arn}/${local.athena_result_prefix}*",
        ]
      },
      {
        Sid    = "WriteAthenaResults"
        Effect = "Allow"
        Action = [
          "s3:AbortMultipartUpload",
          "s3:ListMultipartUploadParts",
          "s3:PutObject",
        ]
        Resource = "${aws_s3_bucket.app_data.arn}/${local.athena_result_prefix}*"
      },
      {
        Sid      = "PublishNotification"
        Effect   = "Allow"
        Action   = "sns:Publish"
        Resource = aws_sns_topic.healthinfo_analysis.arn
      },
      {
        Sid    = "DeliverStepFunctionsLogs"
        Effect = "Allow"
        Action = [
          "logs:CreateLogDelivery",
          "logs:DeleteLogDelivery",
          "logs:DescribeLogGroups",
          "logs:DescribeResourcePolicies",
          "logs:GetLogDelivery",
          "logs:ListLogDeliveries",
          "logs:PutResourcePolicy",
          "logs:UpdateLogDelivery",
        ]
        Resource = "*"
      }
    ]
  })
}

resource "aws_iam_role" "eventbridge_role" {
  name = "${local.resource_prefix}-events-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = "sts:AssumeRole"
        Principal = {
          Service = "events.amazonaws.com"
        }
        Condition = {
          StringEquals = {
            "aws:SourceAccount" = data.aws_caller_identity.current.account_id
          }
          ArnEquals = {
            "aws:SourceArn" = aws_cloudwatch_event_rule.healthinfo_object_created.arn
          }
        }
      }
    ]
  })
}

resource "aws_iam_role_policy" "eventbridge_policy" {
  name = "${local.resource_prefix}-events-policy"
  role = aws_iam_role.eventbridge_role.id
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid      = "StartHealthInfoAnalysis"
        Effect   = "Allow"
        Action   = "states:StartExecution"
        Resource = aws_sfn_state_machine.healthinfo_analysis.arn
      }
    ]
  })
}

resource "aws_iam_role" "amazon_q_role" {
  name = "${local.resource_prefix}-amazon-q-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = "sts:AssumeRole"

        Principal = {
          Service = "chatbot.amazonaws.com"
        }
      }
    ]
  })

}
