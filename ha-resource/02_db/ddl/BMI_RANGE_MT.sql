-- BMI範囲マスタ
CREATE TABLE IF NOT EXISTS BMI_RANGE_MT (
  -- BMI範囲ID
  SEQ_BMI_RANGE_ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT 'BMI範囲ID',
  -- 範囲下限
  RANGE_MIN INT COMMENT '範囲下限',
  -- 範囲上限
  RANGE_MAX INT COMMENT '範囲上限',
  -- 肥満度ステータス
  OVER_WEIGHT_STATUS VARCHAR(2) COMMENT '肥満度ステータス',
  -- 更新日時
  UPDATE_DATE DATETIME NOT NULL COMMENT '更新日時',
  -- 登録日時
  REG_DATE DATETIME NOT NULL COMMENT '登録日時'
);