output "healthinfo_analysis_state_machine_name" {
  value = aws_sfn_state_machine.healthinfo_analysis.name
}

output "healthinfo_analysis_state_machine_arn" {
  value = aws_sfn_state_machine.healthinfo_analysis.arn
}

output "healthinfo_analysis_athena_workgroup_name" {
  value = aws_athena_workgroup.healthinfo.name
}

output "healthinfo_analysis_glue_database_name" {
  value = aws_glue_catalog_database.healthinfo.name
}

output "healthinfo_analysis_glue_table_name" {
  value = aws_glue_catalog_table.health_info.name
}

output "healthinfo_analysis_sns_topic_arn" {
  value = aws_sns_topic.healthinfo_analysis.arn
}

output "healthinfo_analysis_eventbridge_rule_name" {
  value = aws_cloudwatch_event_rule.healthinfo_object_created.name
}

output "healthinfo_analysis_eventbridge_dlq_url" {
  value = aws_sqs_queue.eventbridge_dlq.url
}

output "healthinfo_analysis_athena_result_s3_uri" {
  value = local.athena_result_s3_uri
}

output "healthinfo_analysis_amazon_q_slack_configuration_arn" {
  value = aws_chatbot_slack_channel_configuration.healthinfo_analysis.chat_configuration_arn
}
