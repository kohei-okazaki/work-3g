CREATE TABLE ACCOUNT (
-- ユーザID
USER_ID varchar2(16),
-- パスワード
PASSWORD varchar(16),
-- 削除フラグ
DELETE_FLAG varchar2(1),
-- パスワード有効期限
PASSWORD_EXPIRE date,
-- 備考
REMARKS varchar2(256),
-- ファイル囲い文字利用フラグ
FILE_ENCLOSURE_CHAR_FLAG varchar2(1),
-- 健康情報マスク利用フラグ
HEALTH_INFO_MASK_FLAG varchar2(1),
-- 更新日時
UPDATE_DATE date,
-- 登録日時
REG_DATE date
)