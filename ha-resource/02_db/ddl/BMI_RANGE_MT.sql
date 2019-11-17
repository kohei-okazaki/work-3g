-- BMI範囲マスタ
CREATE TABLE BMI_RANGE_MT (
-- BMI範囲ID
BMI_RANGE_ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY comment 'BMI範囲ID',
-- 範囲下限
RANGE_MIN INT comment '範囲下限',
-- 範囲上限
RANGE_MAX INT comment '範囲上限',
-- 肥満度ステータス
OVER_WEIGHT_STATUS VARCHAR(2) comment '肥満度ステータス',
-- 更新日時
UPDATE_DATE TIMESTAMP comment '更新日時',
-- 登録日時
REG_DATE TIMESTAMP comment '登録日時'
);
