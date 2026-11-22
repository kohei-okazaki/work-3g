resource "aws_chatbot_slack_channel_configuration" "healthinfo_analysis" {

  configuration_name = local.state_machine_name
  iam_role_arn       = aws_iam_role.amazon_q_role.arn
  slack_team_id      = var.slack_team_id
  slack_channel_id   = var.slack_channel_id
  sns_topic_arns     = [aws_sns_topic.healthinfo_analysis.arn]

  guardrail_policy_arns       = ["arn:${data.aws_partition.current.partition}:iam::aws:policy/ReadOnlyAccess"]
  logging_level               = "NONE"
  user_authorization_required = false
}
