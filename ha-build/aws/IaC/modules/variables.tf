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

variable "vpc_cidr" {
  description = "VPCに割り当てるCIDR"
  type        = string
  default     = "10.0.0.0/16"
}

variable "az_count" {
  description = "AZ数"
  type        = number
  default     = 2
}

variable "public_subnet_cidrs" {
  description = "アプリ用サブネットのCIDR"
  type        = list(string)
  default     = ["10.0.0.0/24", "10.0.1.0/24"]
}

variable "private_db_subnet_cidrs" {
  description = "DB用サブネットのCIDR"
  type        = list(string)
  default     = ["10.0.2.0/24", "10.0.3.0/24"]
}

variable "db_name" {
  description = "DB名"
  type        = string
  default     = "work3g"
}

variable "db_engine_version" {
  description = "RDS MySQLエンジンバージョン"
  type        = string
  default     = "8.4.8"
}

variable "db_master_username" {
  description = "RDSマスターユーザ名"
  type        = string
  default     = "healthapp_master"
}

variable "db_master_password_parameter_version" {
  description = "RDSマスターパスワードのSSM SecureStringバージョン。0を指定すると最新バージョンを使用します。"
  type        = number
  default     = 1
}

variable "db_app_username" {
  description = "ha-dashboardおよびha-apiで使用するアプリケーションDBユーザ名"
  type        = string
  default     = "app_user"
}

variable "api_log_queue_name" {
  description = "API通信ログ用のSQS FIFOキュー名"
  type        = string
  default     = "dev_api_log.fifo"

  validation {
    condition     = can(regex("\\.fifo$", var.api_log_queue_name))
    error_message = "api_log_queue_nameは.fifoで終わる必要があります。"
  }
}

variable "app_ssm_parameter_prefix" {
  description = "アプリケーションタスクが読み込むSSM Parameter Storeのパスプレフィックス"
  type        = string
  default     = "/dev"
}

variable "bastion_ssh_allowed_cidr" {
  description = "BastionへのSSHを許可するCIDR。SSM Session ManagerではSSHは不要です。"
  type        = string
  default     = "127.0.0.1/32"
}

variable "bastion_instance_type" {
  description = "BastionのEC2インスタンスタイプ"
  type        = string
  default     = "t3.micro"
}

variable "bastion_key_name" {
  description = "BastionへのSSHに使用する既存EC2キーペア名（任意）"
  type        = string
  default     = ""
}

variable "dashboard_desired_count" {
  description = "ha-dashboard ECSサービスの初期希望タスク数"
  type        = number
  default     = 0
}

variable "api_desired_count" {
  description = "ha-api ECSサービスの初期希望タスク数"
  type        = number
  default     = 0
}

variable "api_public_allowed_cidr" {
  description = "ha-apiのパブリックタスクIPへの直接呼び出しを許可するCIDR。空の場合は外部からのインバウンドを無効化します。"
  type        = string
  default     = "0.0.0.0/0"
}

variable "track_desired_count" {
  description = "ha-track ECSサービスの初期希望タスク数"
  type        = number
  default     = 0
}

variable "track_public_allowed_cidr" {
  description = "ha-trackのパブリックタスクIPへの直接呼び出しを許可するCIDR。空の場合は外部からのインバウンドを無効化します。"
  type        = string
  default     = ""
}

variable "track_django_allowed_hosts" {
  description = "ha-trackのDjango ALLOWED_HOSTS（カンマ区切り）。空の場合はCloud Mapホスト名を使用します。"
  type        = string
  default     = ""
}

variable "root_api_desired_count" {
  description = "ha-root API ECSサービスの初期希望タスク数"
  type        = number
  default     = 0
}

variable "root_api_public_allowed_cidr" {
  description = "ha-root APIのパブリックタスクIPへの直接呼び出しを許可するCIDR。空の場合は外部からのインバウンドを無効化します。"
  type        = string
  default     = "0.0.0.0/0"
}

variable "root_front_url" {
  description = "CORSに使用する既存のha-rootフロントサイトオリジン。例: 管理済みS3 Website URL"
  type        = string
  default     = "http://localhost:8083"
}

variable "app_data_bucket_name" {
  description = "Spring Bootアプリが使用する既存アプリケーションデータ用S3バケット。Terraformではこのバケットを作成しません。"
  type        = string
  default     = "healthinfo-app-dev"
}

variable "image_tag" {
  description = "すべてのECRイメージで使用するDockerイメージタグ"
  type        = string
  default     = "dev"
}

variable "health_info_dashboard_url" {
  description = "HEALTHINFO_DASHBOARD_URLの任意の上書き値"
  type        = string
  default     = ""
}
