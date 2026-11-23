resource "aws_sns_topic" "healthinfo_analysis" {
  name = local.resource_prefix
}
