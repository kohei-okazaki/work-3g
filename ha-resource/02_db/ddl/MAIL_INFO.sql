-- メール情報
CREATE TABLE MAIL_INFO (
-- ユーザID
USER_ID VARCHAR(16) NOT NULL PRIMARY KEY comment 'ユーザID',
-- メールアドレス
MAIL_ADDRESS VARCHAR(108) comment 'メールアドレス',
-- メールパスワード
MAIL_PASSWORD VARCHAR(44) comment 'メールパスワード',
-- 更新日時
UPDATE_DATE TIMESTAMP comment '更新日時',
-- 登録日時
REG_DATE TIMESTAMP comment '登録日時'
);
