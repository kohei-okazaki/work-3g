resource "aws_ecr_repository" "dashboard" {
  name                 = "${local.project_dns_label}/ha-dashboard"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-ha-dashboard"
  })
}

resource "aws_ecr_repository" "api" {
  name                 = "${local.project_dns_label}/ha-api"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-ha-api"
  })
}

resource "aws_ecr_repository" "root_api" {
  name                 = "${local.project_dns_label}/ha-root-api"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-ha-root-api"
  })
}

resource "aws_ecr_repository" "track" {
  name                 = "${local.project_dns_label}/ha-track"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-ha-track"
  })
}

resource "aws_ecr_repository" "batch" {
  name                 = "${local.project_dns_label}/ha-batch"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-ha-batch"
  })
}

resource "aws_ecr_lifecycle_policy" "expire_untagged_images" {
  for_each = {
    dashboard = aws_ecr_repository.dashboard.name
    api       = aws_ecr_repository.api.name
    root_api  = aws_ecr_repository.root_api.name
    track     = aws_ecr_repository.track.name
    batch     = aws_ecr_repository.batch.name
  }

  repository = each.value

  policy = jsonencode({
    rules = [
      {
        rulePriority = 1
        description  = "Expire untagged images one day after they are pushed"
        selection = {
          tagStatus   = "untagged"
          countType   = "sinceImagePushed"
          countUnit   = "days"
          countNumber = 1
        }
        action = {
          type = "expire"
        }
      }
    ]
  })
}

resource "aws_ecs_cluster" "main" {
  name = "${var.project_name}-cluster"

  setting {
    name  = "containerInsights"
    value = "disabled"
  }

  tags = local.common_tags
}

resource "aws_service_discovery_private_dns_namespace" "app" {
  name        = local.service_discovery_namespace_name
  description = "Private DNS namespace for ${var.project_name} app services"
  vpc         = aws_vpc.main.id

  tags = local.common_tags
}

resource "aws_cloudwatch_log_group" "dashboard" {
  name              = "/ecs/${var.project_name}/ha-dashboard"
  retention_in_days = 1

  tags = local.common_tags
}

resource "aws_cloudwatch_log_group" "api" {
  name              = "/ecs/${var.project_name}/ha-api"
  retention_in_days = 1

  tags = local.common_tags
}

resource "aws_cloudwatch_log_group" "root_api" {
  name              = "/ecs/${var.project_name}/ha-root-api"
  retention_in_days = 1

  tags = local.common_tags
}

resource "aws_cloudwatch_log_group" "track" {
  name              = "/ecs/${var.project_name}/ha-track"
  retention_in_days = 1

  tags = local.common_tags
}

resource "aws_cloudwatch_log_group" "batch" {
  name              = "/ecs/${var.project_name}/ha-batch"
  retention_in_days = 1

  tags = local.common_tags
}

resource "aws_ecs_task_definition" "dashboard" {
  family                   = "${var.project_name}-ha-dashboard"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.dashboard_task_execution.arn
  task_role_arn            = aws_iam_role.dashboard_task.arn

  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "X86_64"
  }

  container_definitions = jsonencode([
    {
      name      = "DashboardContainer"
      image     = local.dashboard_image_uri
      essential = true

      portMappings = [
        {
          containerPort = var.dashboard_container_port
          protocol      = "tcp"
        }
      ]

      environment = local.dashboard_environment

      secrets = [
        {
          name      = "DB_PW"
          valueFrom = local.db_app_password_parameter_arn
        }
      ]

      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.dashboard.name
          awslogs-region        = data.aws_region.current.name
          awslogs-stream-prefix = "ha-dashboard"
        }
      }
    }
  ])

  depends_on = [
    aws_iam_role_policy_attachment.dashboard_task_execution_managed,
    aws_iam_role_policy.dashboard_execution_ssm,
    aws_iam_role_policy.dashboard_task_app,
  ]

  tags = local.common_tags
}

resource "aws_ecs_service" "dashboard" {
  name                               = "${var.project_name}-ha-dashboard-service"
  cluster                            = aws_ecs_cluster.main.id
  task_definition                    = aws_ecs_task_definition.dashboard.arn
  desired_count                      = var.dashboard_desired_count
  launch_type                        = "FARGATE"
  deployment_minimum_healthy_percent = 0
  deployment_maximum_percent         = 100

  network_configuration {
    subnets = aws_subnet.public[*].id
    security_groups = [
      aws_security_group.dashboard_ingress.id,
      aws_security_group.shared_db_client.id,
      aws_security_group.internal_app_client.id,
    ]
    assign_public_ip = true
  }

  tags = local.common_tags
}

resource "aws_service_discovery_service" "api" {
  name = var.api_service_discovery_name

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.app.id

    dns_records {
      ttl  = 30
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }

  tags = local.common_tags
}

resource "aws_ecs_task_definition" "api" {
  family                   = "${var.project_name}-ha-api"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.api_task_execution.arn
  task_role_arn            = aws_iam_role.api_task.arn

  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "X86_64"
  }

  container_definitions = jsonencode([
    {
      name      = "ApiContainer"
      image     = local.api_image_uri
      essential = true

      portMappings = [
        {
          containerPort = var.api_container_port
          protocol      = "tcp"
        }
      ]

      environment = local.api_environment

      secrets = [
        {
          name      = "DB_PW"
          valueFrom = local.db_app_password_parameter_arn
        }
      ]

      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.api.name
          awslogs-region        = data.aws_region.current.name
          awslogs-stream-prefix = "ha-api"
        }
      }
    }
  ])

  depends_on = [
    aws_iam_role_policy_attachment.api_task_execution_managed,
    aws_iam_role_policy.api_execution_ssm,
    aws_iam_role_policy.api_task_app,
  ]

  tags = local.common_tags
}

resource "aws_ecs_service" "api" {
  name                               = "${var.project_name}-ha-api-service"
  cluster                            = aws_ecs_cluster.main.id
  task_definition                    = aws_ecs_task_definition.api.arn
  desired_count                      = var.api_desired_count
  launch_type                        = "FARGATE"
  deployment_minimum_healthy_percent = 0
  deployment_maximum_percent         = 100

  network_configuration {
    subnets = aws_subnet.public[*].id
    security_groups = [
      aws_security_group.api_task.id,
      aws_security_group.shared_db_client.id,
      aws_security_group.internal_app_client.id,
    ]
    assign_public_ip = true
  }

  service_registries {
    registry_arn = aws_service_discovery_service.api.arn
  }

  tags = local.common_tags
}

resource "aws_service_discovery_service" "track" {
  name = var.track_service_discovery_name

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.app.id

    dns_records {
      ttl  = 30
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }

  tags = local.common_tags
}

resource "aws_ecs_task_definition" "track" {
  family                   = "${var.project_name}-ha-track"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.track_task_execution.arn
  task_role_arn            = aws_iam_role.track_task.arn

  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "X86_64"
  }

  container_definitions = jsonencode([
    {
      name      = "TrackContainer"
      image     = local.track_image_uri
      essential = true

      portMappings = [
        {
          containerPort = var.track_container_port
          protocol      = "tcp"
        }
      ]

      environment = local.track_environment

      secrets = [
        {
          name      = "DJANGO_SECRET_KEY"
          valueFrom = local.track_django_secret_key_parameter_arn
        }
      ]

      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.track.name
          awslogs-region        = data.aws_region.current.name
          awslogs-stream-prefix = "ha-track"
        }
      }
    }
  ])

  depends_on = [
    aws_iam_role_policy_attachment.track_task_execution_managed,
    aws_iam_role_policy.track_execution_ssm,
    aws_iam_role_policy.track_task_dynamodb,
  ]

  tags = local.common_tags
}

resource "aws_ecs_service" "track" {
  name                               = "${var.project_name}-ha-track-service"
  cluster                            = aws_ecs_cluster.main.id
  task_definition                    = aws_ecs_task_definition.track.arn
  desired_count                      = var.track_desired_count
  launch_type                        = "FARGATE"
  deployment_minimum_healthy_percent = 0
  deployment_maximum_percent         = 100

  network_configuration {
    subnets = aws_subnet.public[*].id
    security_groups = [
      aws_security_group.track_task.id,
    ]
    assign_public_ip = true
  }

  service_registries {
    registry_arn = aws_service_discovery_service.track.arn
  }

  tags = local.common_tags
}

resource "aws_service_discovery_service" "root_api" {
  name = var.root_api_service_discovery_name

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.app.id

    dns_records {
      ttl  = 30
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }

  tags = local.common_tags
}

resource "aws_ecs_task_definition" "root_api" {
  family                   = "${var.project_name}-ha-root-api"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.root_api_task_execution.arn
  task_role_arn            = aws_iam_role.root_api_task.arn

  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "X86_64"
  }

  container_definitions = jsonencode([
    {
      name      = "RootApiContainer"
      image     = local.root_api_image_uri
      essential = true

      portMappings = [
        {
          containerPort = var.root_api_container_port
          protocol      = "tcp"
        }
      ]

      environment = local.root_api_environment

      secrets = [
        {
          name      = "DB_PW"
          valueFrom = local.db_app_password_parameter_arn
        }
      ]

      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.root_api.name
          awslogs-region        = data.aws_region.current.name
          awslogs-stream-prefix = "ha-root-api"
        }
      }
    }
  ])

  depends_on = [
    aws_iam_role_policy_attachment.root_api_task_execution_managed,
    aws_iam_role_policy.root_api_execution_ssm,
    aws_iam_role_policy.root_api_task_app,
    aws_iam_role_policy.root_api_task_aws_access,
  ]

  tags = local.common_tags
}

resource "aws_ecs_service" "root_api" {
  name                               = "${var.project_name}-ha-root-api-service"
  cluster                            = aws_ecs_cluster.main.id
  task_definition                    = aws_ecs_task_definition.root_api.arn
  desired_count                      = var.root_api_desired_count
  launch_type                        = "FARGATE"
  deployment_minimum_healthy_percent = 0
  deployment_maximum_percent         = 100

  network_configuration {
    subnets = aws_subnet.public[*].id
    security_groups = [
      aws_security_group.root_api_task.id,
      aws_security_group.shared_db_client.id,
      aws_security_group.internal_app_client.id,
    ]
    assign_public_ip = true
  }

  service_registries {
    registry_arn = aws_service_discovery_service.root_api.arn
  }

  tags = local.common_tags
}

resource "aws_ecs_task_definition" "batch" {
  family                   = "${var.project_name}-ha-batch"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.batch_task_execution.arn
  task_role_arn            = aws_iam_role.batch_task.arn

  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "X86_64"
  }

  container_definitions = jsonencode([
    {
      name      = "BatchContainer"
      image     = local.batch_image_uri
      essential = true

      environment = local.batch_environment

      secrets = [
        {
          name      = "DB_PW"
          valueFrom = local.db_app_password_parameter_arn
        }
      ]

      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.batch.name
          awslogs-region        = data.aws_region.current.name
          awslogs-stream-prefix = "ha-batch"
        }
      }
    }
  ])

  depends_on = [
    aws_iam_role_policy_attachment.batch_task_execution_managed,
    aws_iam_role_policy.batch_execution_ssm,
    aws_iam_role_policy.batch_task_app,
    aws_iam_role_policy.batch_task_aws_access,
  ]

  tags = local.common_tags
}
