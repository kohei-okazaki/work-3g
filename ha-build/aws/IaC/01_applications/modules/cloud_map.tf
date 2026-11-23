resource "aws_service_discovery_private_dns_namespace" "app" {
  name        = local.service_discovery_namespace_name
  description = "Private DNS namespace for ${local.resource_prefix} app services"
  vpc         = aws_vpc.main.id
}

resource "aws_service_discovery_service" "api" {
  name = local.api_service_discovery_name

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.app.id

    dns_records {
      ttl  = 30
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {}
}

resource "aws_service_discovery_service" "track" {
  name = local.track_service_discovery_name

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.app.id

    dns_records {
      ttl  = 30
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {}
}

resource "aws_service_discovery_service" "root_api" {
  name = local.root_api_service_discovery_name

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.app.id

    dns_records {
      ttl  = 30
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {}
}
