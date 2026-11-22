resource "aws_glue_catalog_database" "healthinfo" {
  name        = local.glue_database_name
  description = "Monthly health information CSV catalog for ${var.app_env}."
}

resource "aws_glue_catalog_table" "health_info" {
  database_name = aws_glue_catalog_database.healthinfo.name
  name          = local.glue_table_name
  table_type    = "EXTERNAL_TABLE"
  description   = "Monthly health information CSV.gz files written by the batch job."

  parameters = {
    EXTERNAL                    = "TRUE"
    classification              = "csv"
    "skip.header.line.count"    = "1"
    "projection.enabled"        = "true"
    "projection.year.type"      = "integer"
    "projection.year.range"     = "2000,2100"
    "projection.year.interval"  = "1"
    "projection.year.digits"    = "4"
    "storage.location.template" = "s3://${aws_s3_bucket.app_data.id}/${local.input_prefix}year=$${year}/"
  }

  partition_keys {
    name = "year"
    type = "string"
  }

  storage_descriptor {
    location      = "s3://${aws_s3_bucket.app_data.id}/${local.input_prefix}"
    compressed    = true
    input_format  = "org.apache.hadoop.mapred.TextInputFormat"
    output_format = "org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat"

    columns {
      name = "seq_user_id"
      type = "bigint"
    }

    columns {
      name = "height"
      type = "double"
    }

    columns {
      name = "weight"
      type = "double"
    }

    columns {
      name = "bmi"
      type = "double"
    }

    columns {
      name = "standard_weight"
      type = "double"
    }

    columns {
      name = "health_info_reg_date"
      type = "string"
    }

    columns {
      name = "seq_bmi_range_mt_id"
      type = "bigint"
    }

    columns {
      name = "reg_date"
      type = "string"
    }

    columns {
      name = "update_date"
      type = "string"
    }

    ser_de_info {
      name                  = "health-info-csv-serde"
      serialization_library = "org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe"

      parameters = {
        "field.delim"          = ","
        "serialization.format" = ","
      }
    }
  }
}
