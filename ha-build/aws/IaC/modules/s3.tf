resource "aws_s3_bucket" "app_data" {
  bucket = "healthinfo-app-${var.app_env}"
}

resource "aws_s3_bucket_ownership_controls" "app_data" {
  bucket = aws_s3_bucket.app_data.id

  rule {
    object_ownership = "BucketOwnerEnforced"
  }
}

resource "aws_s3_bucket_public_access_block" "app_data" {
  bucket = aws_s3_bucket.app_data.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

resource "aws_s3_bucket_server_side_encryption_configuration" "app_data" {
  bucket = aws_s3_bucket.app_data.id

  rule {
    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"
    }
  }
}

resource "aws_s3_bucket_notification" "eventbridge" {
  bucket      = aws_s3_bucket.app_data.id
  eventbridge = true
  depends_on = [aws_cloudwatch_event_target.step_functions]
}

resource "aws_s3_bucket_lifecycle_configuration" "athena_results" {
  bucket = aws_s3_bucket.app_data.id

  rule {
    id     = "expire-healthinfo-athena-results"
    status = "Enabled"

    filter {
      prefix = local.athena_result_prefix
    }

    expiration {
      days = var.athena_result_expiration_days
    }
  }

  rule {
    id     = "abort-incomplete-multipart-uploads"
    status = "Enabled"

    filter {
      prefix = local.athena_result_prefix
    }

    abort_incomplete_multipart_upload {
      days_after_initiation = 1
    }
  }
}
