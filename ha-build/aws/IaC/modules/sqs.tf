resource "aws_sqs_queue" "api_log" {
  name                      = var.api_log_queue_name
  fifo_queue                = true
  message_retention_seconds = 86400
  sqs_managed_sse_enabled   = true

  tags = merge(local.common_tags, {
    Name = var.api_log_queue_name
  })
}
