DROP TABLE IF EXISTS health_info;
CREATE EXTERNAL TABLE health_info(
  seq_user_id string, 
  height string, 
  weight string, 
  bmi string, 
  standard_weight string, 
  health_info_reg_date string, 
  seq_bmi_range_mt_id string, 
  update_date string, 
  reg_date string)
ROW FORMAT DELIMITED 
  FIELDS TERMINATED BY ',' 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  's3://healthinfo-app-local/monthly/healthinfo';