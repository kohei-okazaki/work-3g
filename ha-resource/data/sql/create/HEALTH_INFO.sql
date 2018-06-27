CREATE TABLE HEALTH_INFO (
-- データID
HEALTH_INFO_ID varchar2(3),
-- ユーザID
USER_ID varchar2(16),
-- 身長
HEIGHT number,
-- 体重
WEIGHT number,
-- BMI
BMI number,
-- 標準体重
STANDARD_WEIGHT number,
-- ユーザステータス
USER_STATUS varchar2(2),
-- 登録日時
REG_DATE date
)
