-- 健康情報
CREATE TABLE IF NOT EXISTS HEALTH_INFO (
  -- 健康情報ID
  SEQ_HEALTH_INFO_ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '健康情報ID',
  -- ユーザID
  SEQ_USER_ID BIGINT NOT NULL COMMENT 'ユーザID',
  -- 身長
  HEIGHT DECIMAL(6, 3) COMMENT '身長',
  -- 体重
  WEIGHT DECIMAL(6, 3) COMMENT '体重',
  -- BMI
  BMI DECIMAL(6, 3) COMMENT 'BMI',
  -- 標準体重
  STANDARD_WEIGHT DECIMAL(6, 3) COMMENT '標準体重',
  -- 健康情報ステータス
  HEALTH_INFO_STATUS VARCHAR(2) COMMENT '健康情報ステータス',
  -- 健康情報作成日時
  HEALTH_INFO_REG_DATE DATETIME COMMENT '健康情報作成日時',
  -- BMI範囲マスタID
  SEQ_BMI_RANGE_MT_ID BIGINT COMMENT 'BMI範囲マスタID',
  -- 更新日時
  UPDATE_DATE DATETIME NOT NULL COMMENT '更新日時',
  -- 登録日時
  REG_DATE DATETIME NOT NULL COMMENT '登録日時'
);