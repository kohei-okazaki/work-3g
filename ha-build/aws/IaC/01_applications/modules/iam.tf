data "aws_iam_policy_document" "ecs_tasks_assume_role" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }

    condition {
      test     = "StringEquals"
      variable = "aws:SourceAccount"
      values   = [data.aws_caller_identity.current.account_id]
    }

    condition {
      test     = "ArnLike"
      variable = "aws:SourceArn"
      values   = ["arn:${data.aws_partition.current.partition}:ecs:${data.aws_region.current.region}:${data.aws_caller_identity.current.account_id}:*"]
    }
  }
}

data "aws_iam_policy_document" "ecs_execution_ssm" {
  statement {
    actions = [
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
    ]
    resources = [local.app_ssm_parameter_prefix_arn]
  }
  statement {
    actions   = ["sqs:GetQueueUrl"]
    resources = [aws_sqs_queue.api_log.arn]
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

resource "aws_iam_role_policy" "ecs_task_execution_policy" {
  for_each = {
    dashboard = {
      role           = aws_iam_role.dashboard_task_execution_role.id
      repository_arn = aws_ecr_repository.dashboard.arn
      log_group_arn  = aws_cloudwatch_log_group.dashboard.arn
    }
    api = {
      role           = aws_iam_role.api_task_execution_role.id
      repository_arn = aws_ecr_repository.api.arn
      log_group_arn  = aws_cloudwatch_log_group.api.arn
    }
    root_api = {
      role           = aws_iam_role.root_api_task_execution_role.id
      repository_arn = aws_ecr_repository.root_api.arn
      log_group_arn  = aws_cloudwatch_log_group.root_api.arn
    }
    track = {
      role           = aws_iam_role.track_task_execution_role.id
      repository_arn = aws_ecr_repository.track.arn
      log_group_arn  = aws_cloudwatch_log_group.track.arn
    }
    batch = {
      role           = aws_iam_role.batch_task_execution_role.id
      repository_arn = aws_ecr_repository.batch.arn
      log_group_arn  = aws_cloudwatch_log_group.batch.arn
    }
  }

  name = "${local.resource_prefix}-${each.key}-exec"
  role = each.value.role
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid      = "AuthenticateToEcr"
        Effect   = "Allow"
        Action   = "ecr:GetAuthorizationToken"
        Resource = "*"
      },
      {
        Sid    = "PullTaskImage"
        Effect = "Allow"
        Action = [
          "ecr:BatchCheckLayerAvailability",
          "ecr:GetDownloadUrlForLayer",
          "ecr:BatchGetImage",
        ]
        Resource = each.value.repository_arn
      },
      {
        Sid    = "WriteTaskLogs"
        Effect = "Allow"
        Action = [
          "logs:CreateLogStream",
          "logs:PutLogEvents",
        ]
        Resource = "${each.value.log_group_arn}:log-stream:*"
      },
    ]
  })
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
          "sqs:ReceiveMessage",
        ]
        Resource = [aws_sqs_queue.api_log.arn]
      },
      {
        Effect = "Allow"
        Action = [
          "s3:PutObject",
          "s3:PutObjectAcl",
        ]
        Resource = [
          "${aws_s3_bucket.app_data.arn}/analysis/*",
          "${aws_s3_bucket.app_data.arn}/${local.input_prefix}*",
        ]
        Condition = {
          StringEquals = {
            "aws:ResourceAccount" = data.aws_caller_identity.current.account_id
          }
        }
      },
      {
        Effect = "Allow"
        Action = [
          "ses:SendEmail",
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
          "s3:GetObject",
          "s3:PutObject",
          "s3:PutObjectAcl",
          "s3:DeleteObject",
        ]
        Resource = ["${aws_s3_bucket.app_data.arn}/*"]
        Condition = {
          StringEquals = {
            "aws:ResourceAccount" = data.aws_caller_identity.current.account_id
          }
        }
      },
      {
        Effect = "Allow"
        Action = [
          "ses:SendEmail",
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
