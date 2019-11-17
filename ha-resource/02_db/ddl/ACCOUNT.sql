-- アカウント情報
CREATE TABLE ACCOUNT (
-- ユーザID
USER_ID VARCHAR(16) NOT NULL PRIMARY KEY comment 'ユーザID',
-- パスワード
PASSWORD VARCHAR(44) comment 'パスワード',
-- 削除フラグ
DELETE_FLAG VARCHAR(1) comment '削除フラグ',
-- パスワード有効期限
PASSWORD_EXPIRE DATE comment 'パスワード有効期限',
-- 備考
REMARKS VARCHAR(256) comment '備考',
-- APIキー
API_KEY VARCHAR(64) comment 'APIキー',
-- 更新日時
UPDATE_DATE TIMESTAMP comment '更新日時',
-- 登録日時
REG_DATE TIMESTAMP comment '登録日時'
);
