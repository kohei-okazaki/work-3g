resource "aws_sqs_queue" "api_log" {
  name                      = var.api_log_queue_name
  fifo_queue                = true
  message_retention_seconds = 86400
  sqs_managed_sse_enabled   = true
}

resource "aws_sqs_queue" "eventbridge_dlq" {
  name                      = "${local.healthinfo_analysis_resource_prefix}-event-dlq"
  message_retention_seconds = 1209600
  sqs_managed_sse_enabled   = true
}

resource "aws_sqs_queue_policy" "eventbridge_dlq" {
  queue_url = aws_sqs_queue.eventbridge_dlq.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid       = "AllowEventBridgeDelivery"
        Effect    = "Allow"
        Principal = {
          Service = "events.amazonaws.com"
        }
        Action    = "sqs:SendMessage"
        Resource  = aws_sqs_queue.eventbridge_dlq.arn
        Condition = {
          ArnEquals = {
            "aws:SourceArn" = aws_cloudwatch_event_rule.healthinfo_object_created.arn
          }
          StringEquals = {
            "aws:SourceAccount" = data.aws_caller_identity.current.account_id
          }
        }
      }
    ]
  })
}
