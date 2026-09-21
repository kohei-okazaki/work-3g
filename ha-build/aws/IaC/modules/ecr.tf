resource "aws_ecr_repository" "dashboard" {
  name                 = "${local.resource_dns_label}/ha-dashboard"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

}

resource "aws_ecr_repository" "api" {
  name                 = "${local.resource_dns_label}/ha-api"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

}

resource "aws_ecr_repository" "root_api" {
  name                 = "${local.resource_dns_label}/ha-root-api"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

}

resource "aws_ecr_repository" "track" {
  name                 = "${local.resource_dns_label}/ha-track"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

}

resource "aws_ecr_repository" "batch" {
  name                 = "${local.resource_dns_label}/ha-batch"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

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
