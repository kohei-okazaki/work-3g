resource "aws_sns_topic" "healthinfo_analysis" {
  name = local.healthinfo_analysis_resource_prefix
}
