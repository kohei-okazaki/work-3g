-- 健康情報
CREATE TABLE HEALTH_INFO (
-- 健康情報ID
HEALTH_INFO_ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY comment '健康情報ID',
-- ユーザID
USER_ID VARCHAR(16) comment 'ユーザID',
-- 身長
HEIGHT DECIMAL(6, 3) comment '身長',
-- 体重
WEIGHT DECIMAL(6, 3) comment '体重',
-- BMI
BMI DECIMAL(6, 3) comment 'BMI',
-- 標準体重
STANDARD_WEIGHT DECIMAL(6, 3) comment '標準体重',
-- 健康情報ステータス
HEALTH_INFO_STATUS VARCHAR(2) comment '健康情報ステータス',
-- 健康情報作成日時
HEALTH_INFO_REG_DATE TIMESTAMP comment '健康情報作成日時',
-- BMI範囲ID
BMI_RANGE_ID INT comment 'BMI範囲ID',
-- 更新日時
UPDATE_DATE TIMESTAMP comment '更新日時',
-- 登録日時
REG_DATE TIMESTAMP comment '登録日時'
);
