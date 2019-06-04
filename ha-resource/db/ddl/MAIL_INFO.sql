-- メール情報
CREATE TABLE MAIL_INFO (
-- ユーザID
USER_ID VARCHAR(16) NOT NULL PRIMARY KEY,
-- メールアドレス
MAIL_ADDRESS VARCHAR(108),
-- メールパスワード
MAIL_PASSWORD VARCHAR(44),
-- 更新日時
UPDATE_DATE TIMESTAMP,
-- 登録日時
REG_DATE TIMESTAMP
);
