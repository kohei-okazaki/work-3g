resource "aws_ecs_cluster" "main" {
  name = "${local.resource_prefix}-cluster"

  setting {
    name  = "containerInsights"
    value = "disabled"
  }
}

resource "aws_ecs_task_definition" "dashboard" {
  family                   = "${local.resource_prefix}-ha-dashboard"
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
          containerPort = local.dashboard_container_port
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
          awslogs-region        = data.aws_region.current.region
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
}

resource "aws_ecs_service" "dashboard" {
  name                               = "${local.resource_prefix}-ha-dashboard-service"
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
}

resource "aws_ecs_task_definition" "api" {
  family                   = "${local.resource_prefix}-ha-api"
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
          containerPort = local.api_container_port
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
          awslogs-region        = data.aws_region.current.region
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
}

resource "aws_ecs_service" "api" {
  name                               = "${local.resource_prefix}-ha-api-service"
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
}

resource "aws_ecs_task_definition" "track" {
  family                   = "${local.resource_prefix}-ha-track"
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
          containerPort = local.track_container_port
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
          awslogs-region        = data.aws_region.current.region
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
}

resource "aws_ecs_service" "track" {
  name                               = "${local.resource_prefix}-ha-track-service"
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
}

resource "aws_ecs_task_definition" "root_api" {
  family                   = "${local.resource_prefix}-ha-root-api"
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
          containerPort = local.root_api_container_port
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
          awslogs-region        = data.aws_region.current.region
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
}

resource "aws_ecs_service" "root_api" {
  name                               = "${local.resource_prefix}-ha-root-api-service"
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
}

resource "aws_ecs_task_definition" "batch" {
  family                   = "${local.resource_prefix}-ha-batch"
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
          awslogs-region        = data.aws_region.current.region
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
}
