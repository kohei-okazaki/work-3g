data "aws_ssm_parameter" "db_master_password" {
  name            = local.db_master_password_parameter_selector
  with_decryption = true
}

resource "aws_db_subnet_group" "database" {
  name        = "${local.resource_prefix}-db-subnet-group"
  description = "Subnet group for private RDS MySQL"
  subnet_ids  = aws_subnet.private_db[*].id

}

resource "aws_db_instance" "database" {
  identifier = "${local.resource_dns_label}-database"

  engine         = "mysql"
  engine_version = var.db_engine_version
  instance_class = var.db_instance_class

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

}
