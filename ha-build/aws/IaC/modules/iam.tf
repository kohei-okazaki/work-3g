data "aws_iam_policy_document" "ecs_tasks_assume_role" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

data "aws_iam_policy_document" "ec2_assume_role" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ec2.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "dashboard_task_execution" {
  name               = "${var.project_name}-dashboard-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role" "dashboard_task" {
  name               = "${var.project_name}-dashboard-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role" "api_task_execution" {
  name               = "${var.project_name}-api-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role" "api_task" {
  name               = "${var.project_name}-api-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role" "root_api_task_execution" {
  name               = "${var.project_name}-root-api-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role" "root_api_task" {
  name               = "${var.project_name}-root-api-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role" "track_task_execution" {
  name               = "${var.project_name}-track-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role" "track_task" {
  name               = "${var.project_name}-track-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role" "batch_task_execution" {
  name               = "${var.project_name}-batch-exec-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role" "batch_task" {
  name               = "${var.project_name}-batch-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_tasks_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role_policy_attachment" "dashboard_task_execution_managed" {
  role       = aws_iam_role.dashboard_task_execution.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "api_task_execution_managed" {
  role       = aws_iam_role.api_task_execution.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "root_api_task_execution_managed" {
  role       = aws_iam_role.root_api_task_execution.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "track_task_execution_managed" {
  role       = aws_iam_role.track_task_execution.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "batch_task_execution_managed" {
  role       = aws_iam_role.batch_task_execution.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

data "aws_iam_policy_document" "track_execution_ssm" {
  statement {
    actions = [
      "ssm:GetParameter",
      "ssm:GetParameters",
    ]
    resources = [
      local.track_django_secret_key_parameter_arn,
    ]
  }

  statement {
    actions   = ["kms:Decrypt"]
    resources = ["*"]

    condition {
      test     = "StringEquals"
      variable = "kms:ViaService"
      values   = ["ssm.${data.aws_region.current.name}.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy" "track_execution_ssm" {
  name   = "${var.project_name}-track-exec-ssm"
  role   = aws_iam_role.track_task_execution.id
  policy = data.aws_iam_policy_document.track_execution_ssm.json
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
      values   = ["ssm.${data.aws_region.current.name}.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy" "dashboard_execution_ssm" {
  name   = "${var.project_name}-dashboard-exec-ssm"
  role   = aws_iam_role.dashboard_task_execution.id
  policy = data.aws_iam_policy_document.ecs_execution_ssm.json
}

resource "aws_iam_role_policy" "api_execution_ssm" {
  name   = "${var.project_name}-api-exec-ssm"
  role   = aws_iam_role.api_task_execution.id
  policy = data.aws_iam_policy_document.ecs_execution_ssm.json
}

resource "aws_iam_role_policy" "root_api_execution_ssm" {
  name   = "${var.project_name}-root-api-exec-ssm"
  role   = aws_iam_role.root_api_task_execution.id
  policy = data.aws_iam_policy_document.ecs_execution_ssm.json
}

resource "aws_iam_role_policy" "batch_execution_ssm" {
  name   = "${var.project_name}-batch-exec-ssm"
  role   = aws_iam_role.batch_task_execution.id
  policy = data.aws_iam_policy_document.ecs_execution_ssm.json
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

resource "aws_iam_role_policy" "dashboard_task_app" {
  name   = "${var.project_name}-dashboard-task-app"
  role   = aws_iam_role.dashboard_task.id
  policy = data.aws_iam_policy_document.app_task_policy.json
}

resource "aws_iam_role_policy" "api_task_app" {
  name   = "${var.project_name}-api-task-app"
  role   = aws_iam_role.api_task.id
  policy = data.aws_iam_policy_document.app_task_policy.json
}

resource "aws_iam_role_policy" "root_api_task_app" {
  name   = "${var.project_name}-root-api-task-app"
  role   = aws_iam_role.root_api_task.id
  policy = data.aws_iam_policy_document.app_task_policy.json
}

resource "aws_iam_role_policy" "batch_task_app" {
  name   = "${var.project_name}-batch-task-app"
  role   = aws_iam_role.batch_task.id
  policy = data.aws_iam_policy_document.app_task_policy.json
}

data "aws_iam_policy_document" "batch_task_aws_access" {
  statement {
    actions = [
      "sqs:DeleteMessage",
      "sqs:GetQueueAttributes",
      "sqs:ReceiveMessage",
    ]
    resources = [
      aws_sqs_queue.api_log.arn,
    ]
  }

  statement {
    actions = [
      "s3:ListBucket",
    ]
    resources = [
      "arn:${data.aws_partition.current.partition}:s3:::${var.app_data_bucket_name}",
    ]
  }

  statement {
    actions = [
      "s3:DeleteObject",
      "s3:GetObject",
      "s3:PutObject",
      "s3:PutObjectAcl",
    ]
    resources = [
      "arn:${data.aws_partition.current.partition}:s3:::${var.app_data_bucket_name}/*",
    ]
  }

  statement {
    actions = [
      "ses:SendEmail",
      "ses:SendRawEmail",
      "ses:VerifyEmailIdentity",
    ]
    resources = ["*"]
  }
}

resource "aws_iam_role_policy" "batch_task_aws_access" {
  name   = "${var.project_name}-batch-task-aws-access"
  role   = aws_iam_role.batch_task.id
  policy = data.aws_iam_policy_document.batch_task_aws_access.json
}

data "aws_iam_policy_document" "root_api_task_aws_access" {
  statement {
    actions = [
      "s3:ListBucket",
    ]
    resources = [
      "arn:${data.aws_partition.current.partition}:s3:::${var.app_data_bucket_name}",
    ]
  }

  statement {
    actions = [
      "s3:GetObject",
      "s3:PutObject",
      "s3:PutObjectAcl",
      "s3:DeleteObject",
    ]
    resources = [
      "arn:${data.aws_partition.current.partition}:s3:::${var.app_data_bucket_name}/*",
    ]
  }

  statement {
    actions = [
      "ses:SendEmail",
      "ses:SendRawEmail",
      "ses:VerifyEmailIdentity",
    ]
    resources = ["*"]
  }
}

resource "aws_iam_role_policy" "root_api_task_aws_access" {
  name   = "${var.project_name}-root-api-task-aws-access"
  role   = aws_iam_role.root_api_task.id
  policy = data.aws_iam_policy_document.root_api_task_aws_access.json
}

data "aws_iam_policy_document" "track_task_dynamodb" {
  statement {
    actions = [
      "dynamodb:PutItem",
    ]
    resources = [
      aws_dynamodb_table.health_info.arn,
    ]
  }
}

resource "aws_iam_role_policy" "track_task_dynamodb" {
  name   = "${var.project_name}-track-task-dynamodb"
  role   = aws_iam_role.track_task.id
  policy = data.aws_iam_policy_document.track_task_dynamodb.json
}

resource "aws_iam_role" "bastion" {
  name               = "${var.project_name}-bastion-role"
  assume_role_policy = data.aws_iam_policy_document.ec2_assume_role.json
  tags               = local.common_tags
}

resource "aws_iam_role_policy_attachment" "bastion_ssm" {
  role       = aws_iam_role.bastion.name
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_instance_profile" "bastion" {
  name = "${var.project_name}-bastion-profile"
  role = aws_iam_role.bastion.name
}
