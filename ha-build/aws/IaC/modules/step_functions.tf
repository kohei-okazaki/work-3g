resource "aws_sfn_state_machine" "healthinfo_analysis" {
  name     = local.state_machine_name
  role_arn = aws_iam_role.step_functions_role.arn
  type     = "STANDARD"

  logging_configuration {
    include_execution_data = false
    level                  = "ERROR"
    log_destination        = "${aws_cloudwatch_log_group.step_functions.arn}:*"
  }

  tracing_configuration {
    enabled = false
  }

  definition = jsonencode({
    Comment = "Count records in the newly uploaded monthly health information file and publish a masked summary."
    StartAt = "ExtractObjectContext"
    States = {
      ExtractObjectContext = {
        Type = "Pass"
        Parameters = {
          "eventId.$"  = "$.id"
          "bucket.$"   = "$.detail.bucket.name"
          "key.$"      = "$.detail.object.key"
          "keyParts.$" = "States.StringSplit($.detail.object.key, '/')"
        }
        Next = "ParseYear"
      }

      ParseYear = {
        Type = "Pass"
        Parameters = {
          "eventId.$" = "$.eventId"
          "bucket.$"  = "$.bucket"
          "key.$"     = "$.key"
          "year.$"    = "States.ArrayGetItem(States.StringSplit(States.ArrayGetItem($.keyParts, 2), '='), 1)"
        }
        Next = "RunAthenaQuery"
      }

      RunAthenaQuery = {
        Type           = "Task"
        Resource       = "arn:${data.aws_partition.current.partition}:states:::athena:startQueryExecution.sync"
        TimeoutSeconds = 300
        Parameters = {
          QueryString = "SELECT COUNT(*) AS record_count FROM \"${local.glue_table_name}\" WHERE \"year\" = ? AND \"$path\" = ?"
          QueryExecutionContext = {
            Catalog  = "AwsDataCatalog"
            Database = local.glue_database_name
          }
          WorkGroup               = "${local.resource_prefix}-athena-workgroup"
          "ClientRequestToken.$"  = "States.Hash($.eventId, 'SHA-256')"
          "ExecutionParameters.$" = "States.Array(States.Format('\\'{}\\'', $.year), States.Format('\\'s3://{}/{}\\'', $.bucket, $.key))"
        }
        ResultSelector = {
          "queryExecutionId.$" = "$.QueryExecution.QueryExecutionId"
        }
        ResultPath = "$.athena"
        Retry = [
          {
            ErrorEquals = [
              "Athena.InternalServerException",
              "Athena.TooManyRequestsException",
            ]
            IntervalSeconds = 2
            MaxAttempts     = 3
            BackoffRate     = 2
          }
        ]
        Catch = [
          {
            ErrorEquals = ["States.ALL"]
            ResultPath  = null
            Next        = "PrepareFailureNotification"
          }
        ]
        Next = "GetQueryResults"
      }

      GetQueryResults = {
        Type     = "Task"
        Resource = "arn:${data.aws_partition.current.partition}:states:::athena:getQueryResults"
        Parameters = {
          "QueryExecutionId.$" = "$.athena.queryExecutionId"
          MaxResults           = 2
        }
        ResultSelector = {
          "recordCount.$" = "$.ResultSet.Rows[1].Data[0].VarCharValue"
        }
        ResultPath = "$.summary"
        Retry = [
          {
            ErrorEquals = [
              "Athena.InternalServerException",
              "Athena.TooManyRequestsException",
            ]
            IntervalSeconds = 2
            MaxAttempts     = 3
            BackoffRate     = 2
          }
        ]
        Catch = [
          {
            ErrorEquals = ["States.ALL"]
            ResultPath  = null
            Next        = "PrepareFailureNotification"
          }
        ]
        Next = "HasRecords"
      }

      HasRecords = {
        Type = "Choice"
        Choices = [
          {
            Variable     = "$.summary.recordCount"
            StringEquals = "0"
            Next         = "PrepareNoDataNotification"
          }
        ]
        Default = "PrepareSuccessNotification"
      }

      PrepareNoDataNotification = {
        Type = "Pass"
        Parameters = {
          version = "1.0"
          source  = "custom"
          content = {
            textType        = "client-markdown"
            title           = "Health information analysis completed (0 records)"
            "description.$" = "States.Format('Input: `s3://{}/{}` | Record count: `0` | Athena QueryExecutionId: `{}` | Results: `${local.athena_result_s3_uri}`. Health information rows are not included in this notification.', $.bucket, $.key, $.athena.queryExecutionId)"
          }
          metadata = {
            "threadId.$" = "States.Format('healthinfo-monthly-{}', $.eventId)"
            summary      = "Health information analysis completed with no records"
          }
        }
        ResultPath = "$.notification"
        Next       = "NotifySuccess"
      }

      PrepareSuccessNotification = {
        Type = "Pass"
        Parameters = {
          version = "1.0"
          source  = "custom"
          content = {
            textType        = "client-markdown"
            title           = "Health information analysis completed"
            "description.$" = "States.Format('Input: `s3://{}/{}` | Record count: `{}` | Athena QueryExecutionId: `{}` | Results: `${local.athena_result_s3_uri}`. Health information rows are not included in this notification.', $.bucket, $.key, $.summary.recordCount, $.athena.queryExecutionId)"
          }
          metadata = {
            "threadId.$" = "States.Format('healthinfo-monthly-{}', $.eventId)"
            summary      = "Health information analysis completed"
          }
        }
        ResultPath = "$.notification"
        Next       = "NotifySuccess"
      }

      NotifySuccess = {
        Type     = "Task"
        Resource = "arn:${data.aws_partition.current.partition}:states:::sns:publish"
        Parameters = {
          TopicArn    = aws_sns_topic.healthinfo_analysis.arn
          "Message.$" = "States.JsonToString($.notification)"
        }
        Next = "AnalysisSucceeded"
      }

      AnalysisSucceeded = {
        Type = "Succeed"
      }

      PrepareFailureNotification = {
        Type = "Pass"
        Parameters = {
          version = "1.0"
          source  = "custom"
          content = {
            textType        = "client-markdown"
            title           = "Health information analysis failed"
            "description.$" = "States.Format('Input: `s3://{}/{}` | Step Functions execution: `{}`. Error details are omitted from Slack; inspect the execution history.', $.bucket, $.key, $$.Execution.Name)"
          }
          metadata = {
            "threadId.$" = "States.Format('healthinfo-monthly-{}', $.eventId)"
            summary      = "Health information analysis failed"
          }
        }
        ResultPath = "$.notification"
        Next       = "NotifyFailure"
      }

      NotifyFailure = {
        Type     = "Task"
        Resource = "arn:${data.aws_partition.current.partition}:states:::sns:publish"
        Parameters = {
          TopicArn    = aws_sns_topic.healthinfo_analysis.arn
          "Message.$" = "States.JsonToString($.notification)"
        }
        Next = "AnalysisFailed"
      }

      AnalysisFailed = {
        Type  = "Fail"
        Error = "HealthInfoAnalysisFailed"
        Cause = "Athena query execution or result retrieval failed."
      }
    }
  })

  depends_on = [aws_iam_role_policy.step_functions_policy]
}
