resource "aws_dynamodb_table" "health_info" {
  name         = "health_info_${var.app_env}"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "seq_user_id"
  range_key    = "created_at_epoch"

  deletion_protection_enabled = false

  attribute {
    name = "seq_user_id"
    type = "N"
  }

  attribute {
    name = "created_at_epoch"
    type = "N"
  }

  point_in_time_recovery {
    enabled = var.app_env == "prd"
  }

  server_side_encryption {
    enabled = true
  }

  tags = merge(local.common_tags, {
    Name        = "health_info_${var.app_env}"
    Environment = var.app_env
  })
}
