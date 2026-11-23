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
        Sid    = "ReadBucketMetadata"
        Effect = "Allow"
        Action = [
          "s3:GetBucketLocation",
          "s3:ListBucketMultipartUploads",
        ]
        Resource = local.source_bucket_arn
      },
      {
        Sid      = "ListInputAndResultPrefixes"
        Effect   = "Allow"
        Action   = "s3:ListBucket"
        Resource = local.source_bucket_arn
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
          "${local.source_bucket_arn}/${local.input_prefix}*",
          "${local.source_bucket_arn}/${local.athena_result_prefix}*",
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
        Resource = "${local.source_bucket_arn}/${local.athena_result_prefix}*"
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
