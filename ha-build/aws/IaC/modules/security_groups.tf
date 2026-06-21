resource "aws_security_group" "shared_db_client" {
  name        = "${var.project_name}-shared-db-client-sg"
  description = "Attach this SG to app services to reach RDS"
  vpc_id      = aws_vpc.main.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-shared-db-client-sg"
  })
}

resource "aws_security_group" "internal_app_client" {
  name        = "${var.project_name}-internal-app-client-sg"
  description = "Attach this SG to app services that call internal app endpoints"
  vpc_id      = aws_vpc.main.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-internal-app-client-sg"
  })
}

resource "aws_security_group" "db" {
  name        = "${var.project_name}-db-sg"
  description = "RDS MySQL SG"
  vpc_id      = aws_vpc.main.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-db-sg"
  })
}

resource "aws_security_group_rule" "db_from_app" {
  type                     = "ingress"
  security_group_id        = aws_security_group.db.id
  source_security_group_id = aws_security_group.shared_db_client.id
  from_port                = 3306
  to_port                  = 3306
  protocol                 = "tcp"
  description              = "Allow MySQL from app DB client SG"
}

resource "aws_security_group" "bastion" {
  name        = "${var.project_name}-bastion-sg"
  description = "SSH bastion host SG for manual RDS setup"
  vpc_id      = aws_vpc.main.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-bastion-sg"
  })
}

resource "aws_security_group_rule" "bastion_ssh" {
  type              = "ingress"
  security_group_id = aws_security_group.bastion.id
  cidr_blocks       = [var.bastion_ssh_allowed_cidr]
  from_port         = 22
  to_port           = 22
  protocol          = "tcp"
  description       = "Allow SSH to bastion host"
}

resource "aws_security_group_rule" "db_from_bastion" {
  type                     = "ingress"
  security_group_id        = aws_security_group.db.id
  source_security_group_id = aws_security_group.bastion.id
  from_port                = 3306
  to_port                  = 3306
  protocol                 = "tcp"
  description              = "Allow MySQL from bastion host"
}

resource "aws_security_group" "dashboard_ingress" {
  name        = "${var.project_name}-dashboard-web-sg"
  description = "Internet ingress for ha-dashboard without ALB"
  vpc_id      = aws_vpc.main.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-dashboard-web-sg"
  })
}

resource "aws_security_group_rule" "dashboard_public_ingress" {
  type              = "ingress"
  security_group_id = aws_security_group.dashboard_ingress.id
  cidr_blocks       = ["0.0.0.0/0"]
  from_port         = var.dashboard_container_port
  to_port           = var.dashboard_container_port
  protocol          = "tcp"
  description       = "Allow direct web access to ha-dashboard"
}

resource "aws_security_group" "api_task" {
  name        = "${var.project_name}-api-task-sg"
  description = "Ingress for ha-api tasks from internet and ha-dashboard"
  vpc_id      = aws_vpc.main.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-api-task-sg"
  })
}

resource "aws_security_group_rule" "api_public_ingress" {
  count = var.api_public_allowed_cidr != "" ? 1 : 0

  type              = "ingress"
  security_group_id = aws_security_group.api_task.id
  cidr_blocks       = [var.api_public_allowed_cidr]
  from_port         = var.api_container_port
  to_port           = var.api_container_port
  protocol          = "tcp"
  description       = "Allow direct external access to ha-api"
}

resource "aws_security_group_rule" "api_from_dashboard" {
  type                     = "ingress"
  security_group_id        = aws_security_group.api_task.id
  source_security_group_id = aws_security_group.internal_app_client.id
  from_port                = var.api_container_port
  to_port                  = var.api_container_port
  protocol                 = "tcp"
  description              = "Allow ha-dashboard to call ha-api"
}

resource "aws_security_group" "track_task" {
  name        = "${var.project_name}-track-task-sg"
  description = "Ingress for ha-track tasks from internal app services and optional external clients"
  vpc_id      = aws_vpc.main.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-track-task-sg"
  })
}

resource "aws_security_group_rule" "track_from_internal_app" {
  type                     = "ingress"
  security_group_id        = aws_security_group.track_task.id
  source_security_group_id = aws_security_group.internal_app_client.id
  from_port                = var.track_container_port
  to_port                  = var.track_container_port
  protocol                 = "tcp"
  description              = "Allow internal app services to call ha-track"
}

resource "aws_security_group_rule" "track_public_ingress" {
  count = var.track_public_allowed_cidr != "" ? 1 : 0

  type              = "ingress"
  security_group_id = aws_security_group.track_task.id
  cidr_blocks       = [var.track_public_allowed_cidr]
  from_port         = var.track_container_port
  to_port           = var.track_container_port
  protocol          = "tcp"
  description       = "Allow direct external access to ha-track"
}

resource "aws_security_group" "root_api_task" {
  name        = "${var.project_name}-root-api-task-sg"
  description = "Ingress for ha-root API tasks from internet and internal app services"
  vpc_id      = aws_vpc.main.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${var.project_name}-root-api-task-sg"
  })
}

resource "aws_security_group_rule" "root_api_public_ingress" {
  count = var.root_api_public_allowed_cidr != "" ? 1 : 0

  type              = "ingress"
  security_group_id = aws_security_group.root_api_task.id
  cidr_blocks       = [var.root_api_public_allowed_cidr]
  from_port         = var.root_api_container_port
  to_port           = var.root_api_container_port
  protocol          = "tcp"
  description       = "Allow direct external access to ha-root API"
}

resource "aws_security_group_rule" "root_api_from_internal_app" {
  type                     = "ingress"
  security_group_id        = aws_security_group.root_api_task.id
  source_security_group_id = aws_security_group.internal_app_client.id
  from_port                = var.root_api_container_port
  to_port                  = var.root_api_container_port
  protocol                 = "tcp"
  description              = "Allow internal app services to call ha-root API"
}
