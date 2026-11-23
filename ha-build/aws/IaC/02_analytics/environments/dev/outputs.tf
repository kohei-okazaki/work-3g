output "healthinfo_analysis_state_machine_name" {
  value = module.analytics.healthinfo_analysis_state_machine_name
}

output "healthinfo_analysis_state_machine_arn" {
  value = module.analytics.healthinfo_analysis_state_machine_arn
}

output "healthinfo_analysis_athena_workgroup_name" {
  value = module.analytics.healthinfo_analysis_athena_workgroup_name
}

output "healthinfo_analysis_glue_database_name" {
  value = module.analytics.healthinfo_analysis_glue_database_name
}

output "healthinfo_analysis_glue_table_name" {
  value = module.analytics.healthinfo_analysis_glue_table_name
}

output "healthinfo_analysis_sns_topic_arn" {
  value = module.analytics.healthinfo_analysis_sns_topic_arn
}

output "healthinfo_analysis_eventbridge_rule_name" {
  value = module.analytics.healthinfo_analysis_eventbridge_rule_name
}

output "healthinfo_analysis_eventbridge_dlq_url" {
  value = module.analytics.healthinfo_analysis_eventbridge_dlq_url
}

output "healthinfo_analysis_athena_result_s3_uri" {
  value = module.analytics.healthinfo_analysis_athena_result_s3_uri
}

output "healthinfo_analysis_amazon_q_slack_configuration_arn" {
  value = module.analytics.healthinfo_analysis_amazon_q_slack_configuration_arn
}
