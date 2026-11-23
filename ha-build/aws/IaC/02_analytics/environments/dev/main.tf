module "analytics" {
  source = "../../modules"

  project_name                          = var.project_name
  app_env                               = var.app_env
  athena_bytes_scanned_cutoff_per_query = var.athena_bytes_scanned_cutoff_per_query
  step_functions_log_retention_days     = var.step_functions_log_retention_days
  slack_team_id                         = var.slack_team_id
  slack_channel_id                      = var.slack_channel_id
  source_bucket_name                    = var.source_bucket_name
  source_data_prefix                    = var.source_data_prefix
  athena_result_prefix                  = var.athena_result_prefix
}

moved {
  from = module.healthinfo_analysis
  to   = module.analytics
}
