DROP TABLE IF EXISTS health_info;
CREATE EXTERNAL TABLE health_info (
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
  'skip.header.line.count'='1'
)
STORED AS TEXTFILE
LOCATION 's3://healthinfo-app-local/monthly/healthinfo/';

-- Athenaで検索がうまくできない場合、パーティションが聞いていない可能性があるため以下コマンドを実施
MSCK REPAIR TABLE health_info;
SHOW PARTITIONS health_info;
