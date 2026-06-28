data "aws_ssm_parameter" "db_master_password" {
  name            = local.db_master_password_parameter_selector
  with_decryption = true
}

resource "aws_db_subnet_group" "database" {
  name        = "${local.resource_prefix}-db-subnet-group"
  description = "Subnet group for private RDS MySQL"
  subnet_ids  = aws_subnet.private_db[*].id

  tags = merge(local.common_tags, {
    Name = "${local.resource_prefix}-db-subnet-group"
  })
}

resource "aws_db_instance" "database" {
  identifier = "${local.resource_dns_label}-database"

  engine         = "mysql"
  engine_version = var.db_engine_version
  instance_class = "db.t3.micro"

  allocated_storage = 20
  storage_type      = "gp3"
  multi_az          = false

  username = var.db_master_username
  password = data.aws_ssm_parameter.db_master_password.value

  db_subnet_group_name   = aws_db_subnet_group.database.name
  vpc_security_group_ids = [aws_security_group.db.id]
  publicly_accessible    = false

  backup_retention_period    = 0
  delete_automated_backups   = true
  deletion_protection        = false
  skip_final_snapshot        = true
  auto_minor_version_upgrade = true
  apply_immediately          = true

  tags = merge(local.common_tags, {
    Name = "${local.resource_prefix}-database"
  })
}

data "aws_ami" "amazon_linux2" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn2-ami-hvm-*-x86_64-gp2"]
  }

  filter {
    name   = "architecture"
    values = ["x86_64"]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }
}

resource "aws_instance" "bastion" {
  ami                         = data.aws_ami.amazon_linux2.id
  instance_type               = var.bastion_instance_type
  subnet_id                   = aws_subnet.public[0].id
  associate_public_ip_address = true
  vpc_security_group_ids      = [aws_security_group.bastion.id]
  iam_instance_profile        = aws_iam_instance_profile.bastion.name
  key_name                    = var.bastion_key_name != "" ? var.bastion_key_name : null

  metadata_options {
    http_tokens = "required"
  }

  user_data = <<-USERDATA
    #!/bin/bash
    set -eux
    yum install -y mariadb
  USERDATA

  tags = merge(local.common_tags, {
    Name = "${local.resource_prefix}-bastion"
  })
}
