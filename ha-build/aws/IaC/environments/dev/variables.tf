variable "aws_region" {
  description = "AWS region to deploy into."
  type        = string
  default     = "ap-northeast-1"
}

variable "project_name" {
  description = "Project name used for AWS resource names."
  type        = string
  default     = "healthinfo-app"
}

variable "app_env" {
  description = "Application environment used for environment-specific AWS resources."
  type        = string
  default     = "dev"

  validation {
    condition     = contains(["dev", "prd"], var.app_env)
    error_message = "app_env must be either dev or prd."
  }
}

variable "vpc_cidr" {
  description = "CIDR block for the VPC."
  type        = string
  default     = "10.0.0.0/16"
}

variable "az_count" {
  description = "Number of AZs to use. The CDK version uses 2."
  type        = number
  default     = 2
}

variable "public_subnet_cidrs" {
  description = "CIDR blocks for public app subnets."
  type        = list(string)
  default     = ["10.0.0.0/24", "10.0.1.0/24"]
}

variable "private_db_subnet_cidrs" {
  description = "CIDR blocks for private isolated DB subnets."
  type        = list(string)
  default     = ["10.0.2.0/24", "10.0.3.0/24"]
}

variable "db_name" {
  description = "Application database name. Terraform does not create this schema; create it manually as in the CDK flow."
  type        = string
  default     = "work3g"
}

variable "db_engine_version" {
  description = "RDS MySQL engine version."
  type        = string
  default     = "8.4.8"
}

variable "db_master_username" {
  description = "RDS master user name."
  type        = string
  default     = "healthapp_master"
}

variable "db_master_password_parameter_name" {
  description = "Existing SSM SecureString name for the RDS master password. Empty means /$${project_name}/db/master/password."
  type        = string
  default     = ""
}

variable "db_master_password_parameter_version" {
  description = "SSM SecureString version for the RDS master password. Set 0 to use the latest version."
  type        = number
  default     = 1
}

variable "db_app_username" {
  description = "Application DB user name used by ha-dashboard and ha-api."
  type        = string
  default     = "app_user"
}

variable "db_app_password_parameter_name" {
  description = "Existing SSM SecureString name for the application DB password. Empty means /$${project_name}/db/app/password."
  type        = string
  default     = ""
}

variable "db_app_password_parameter_version" {
  description = "Kept for parity with CDK. ECS uses the SSM parameter ARN and reads the current value at task start."
  type        = number
  default     = 1
}

variable "api_log_queue_name" {
  description = "SQS FIFO queue name for API communication logs."
  type        = string
  default     = "dev_api_log.fifo"

  validation {
    condition     = can(regex("\\.fifo$", var.api_log_queue_name))
    error_message = "api_log_queue_name must end with .fifo."
  }
}

variable "app_ssm_parameter_prefix" {
  description = "SSM Parameter Store path prefix read by application tasks."
  type        = string
  default     = "/dev"
}

variable "bastion_ssh_allowed_cidr" {
  description = "CIDR allowed to SSH to the bastion. SSM Session Manager does not require SSH."
  type        = string
  default     = "127.0.0.1/32"
}

variable "bastion_instance_type" {
  description = "EC2 instance type for the bastion."
  type        = string
  default     = "t3.micro"
}

variable "bastion_key_name" {
  description = "Optional existing EC2 key pair name for SSH to bastion."
  type        = string
  default     = ""
}

variable "service_discovery_namespace_name" {
  description = "Cloud Map private DNS namespace. Empty means <sanitized-project-name>.local."
  type        = string
  default     = ""
}

variable "dashboard_container_port" {
  description = "ha-dashboard container and public direct access port for dev."
  type        = number
  default     = 80

  validation {
    condition     = var.dashboard_container_port == 80
    error_message = "dashboard_container_port must be 80 in dev."
  }
}

variable "dashboard_desired_count" {
  description = "Initial desired count for ha-dashboard ECS service."
  type        = number
  default     = 0
}

variable "api_container_port" {
  description = "ha-api container and public direct access port for dev."
  type        = number
  default     = 80

  validation {
    condition     = var.api_container_port == 80
    error_message = "api_container_port must be 80 in dev."
  }
}

variable "api_desired_count" {
  description = "Initial desired count for ha-api ECS service."
  type        = number
  default     = 0
}

variable "api_public_allowed_cidr" {
  description = "CIDR allowed to call ha-api public task IP directly. Empty disables external ingress."
  type        = string
  default     = "0.0.0.0/0"
}

variable "api_service_discovery_name" {
  description = "Cloud Map service name for ha-api."
  type        = string
  default     = "ha-api"
}

variable "track_container_port" {
  description = "ha-track container and public direct access port."
  type        = number
  default     = 8086

  validation {
    condition     = var.track_container_port == 8086
    error_message = "track_container_port must be 8086."
  }
}

variable "track_desired_count" {
  description = "Initial desired count for ha-track ECS service."
  type        = number
  default     = 0
}

variable "track_public_allowed_cidr" {
  description = "CIDR allowed to call ha-track public task IP directly. Empty disables external ingress."
  type        = string
  default     = ""
}

variable "track_service_discovery_name" {
  description = "Cloud Map service name for ha-track."
  type        = string
  default     = "ha-track"
}

variable "track_django_allowed_hosts" {
  description = "Comma-separated Django ALLOWED_HOSTS for ha-track. Empty uses the Cloud Map hostname."
  type        = string
  default     = ""
}

variable "track_django_secret_key_parameter_name" {
  description = "Existing SSM SecureString name for the ha-track Django secret key. Empty means /$${project_name}/ha-track/django-secret-key."
  type        = string
  default     = ""
}

variable "root_api_container_port" {
  description = "ha-root API container and public direct access port for dev."
  type        = number
  default     = 80

  validation {
    condition     = var.root_api_container_port == 80
    error_message = "root_api_container_port must be 80 in dev."
  }
}

variable "root_api_desired_count" {
  description = "Initial desired count for ha-root API ECS service."
  type        = number
  default     = 0
}

variable "root_api_public_allowed_cidr" {
  description = "CIDR allowed to call ha-root API public task IP directly. Empty disables external ingress."
  type        = string
  default     = "0.0.0.0/0"
}

variable "root_api_service_discovery_name" {
  description = "Cloud Map service name for ha-root API."
  type        = string
  default     = "ha-root"
}

variable "root_front_url" {
  description = "Existing ha-root front site origin for CORS, for example an already-managed S3 website URL."
  type        = string
  default     = "http://localhost:8083"
}

variable "app_data_bucket_name" {
  description = "Existing application data S3 bucket used by the Spring Boot apps. Terraform does not create this bucket."
  type        = string
  default     = "healthinfo-app-dev"
}

variable "image_tag" {
  description = "Docker image tag used by all ECR images."
  type        = string
  default     = "dev"
}

variable "health_info_dashboard_url" {
  description = "Optional HEALTHINFO_DASHBOARD_URL override."
  type        = string
  default     = ""
}

variable "health_info_api_url" {
  description = "Optional HEALTHINFO_API_URL override. Empty makes ha-dashboard call ha-api through Cloud Map private DNS."
  type        = string
  default     = ""
}

variable "root_api_url" {
  description = "Optional ROOT_API_URL override."
  type        = string
  default     = ""
}

variable "health_info_track_api_url" {
  description = "Optional HEALTHINFO_TRACK_API_URL override."
  type        = string
  default     = ""
}
