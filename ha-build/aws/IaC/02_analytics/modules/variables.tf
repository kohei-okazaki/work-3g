terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}

variable "project_name" {
  description = "プロジェクト名"
  type        = string
  default     = "healthinfo-app"
}

variable "app_env" {
  description = "アプリケーションの環境名"
  type        = string
  default     = "dev"

  validation {
    condition     = contains(["dev", "stg", "prd"], var.app_env)
    error_message = "app_envはdev、stg、prdのいずれかを指定してください。"
  }
}

variable "athena_bytes_scanned_cutoff_per_query" {
  description = "Athena query 1回当たりの最大scan byte数"
  type        = number
  default     = 104857600

  validation {
    condition     = var.athena_bytes_scanned_cutoff_per_query >= 10000000
    error_message = "Athenaのquery scan上限は10,000,000 byte以上にしてください。"
  }
}

variable "step_functions_log_retention_days" {
  description = "Step FunctionsのERROR logをCloudWatch Logsに保持する日数"
  type        = number
  default     = 1

  validation {
    condition = contains([
      1, 3, 5, 7, 14, 30, 60, 90, 120, 150, 180, 365,
      400, 545, 731, 1096, 1827, 2192, 2557, 2922, 3288, 3653,
    ], var.step_functions_log_retention_days)
    error_message = "step_functions_log_retention_daysはCloudWatch Logsが対応する保持日数にしてください。"
  }
}

variable "slack_team_id" {
  description = "Amazon Qで認可済みのSlack Workspace ID。"
  type        = string
  default     = ""
}

variable "slack_channel_id" {
  description = "通知先Slack Channel ID。"
  type        = string
  default     = ""
}

variable "source_bucket_name" {
  description = "アプリ基盤で作成された共用S3バケット名"
  type        = string

  validation {
    condition     = can(regex("^[a-z0-9][a-z0-9.-]{1,61}[a-z0-9]$", var.source_bucket_name))
    error_message = "source_bucket_nameにはS3 URIではなくバケット名を指定してください。"
  }
}

variable "source_data_prefix" {
  description = "アプリ基盤が公開する月次CSVのS3プレフィックス"
  type        = string
  default     = "monthly/healthinfo/"

  validation {
    condition     = length(var.source_data_prefix) > 1 && !startswith(var.source_data_prefix, "/") && endswith(var.source_data_prefix, "/") && !strcontains(var.source_data_prefix, "*") && !strcontains(var.source_data_prefix, "?")
    error_message = "source_data_prefixは先頭に/を付けず、末尾を/にし、ワイルドカードを含めないでください。"
  }
}

variable "athena_result_prefix" {
  description = "アプリ基盤が保持期間を管理するAthena結果のS3プレフィックス"
  type        = string
  default     = "monthly/athena-results/"

  validation {
    condition     = length(var.athena_result_prefix) > 1 && !startswith(var.athena_result_prefix, "/") && endswith(var.athena_result_prefix, "/") && !strcontains(var.athena_result_prefix, "*") && !strcontains(var.athena_result_prefix, "?")
    error_message = "athena_result_prefixは先頭に/を付けず、末尾を/にし、ワイルドカードを含めないでください。"
  }
}
