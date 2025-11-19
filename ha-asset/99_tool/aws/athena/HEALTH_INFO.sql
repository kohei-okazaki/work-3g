DROP TABLE IF EXISTS health;
CREATE EXTERNAL TABLE health (
  seq_user_id         bigint,
  height              double,
  weight              double,
  bmi                 double,
  standard_weight     double,
  created_at          string,
  bmi_range_mt_id     bigint,
  reg_date            string,
  update_date         string
)
PARTITIONED BY (year string)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
  'field.delim' = ',',
  'skip.header.line.count'='0'
)
STORED AS TEXTFILE
LOCATION 's3://healthinfo-app-local/monthly/healthinfo/';