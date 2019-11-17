-- 健康情報ファイル設定
CREATE TABLE HEALTH_INFO_FILE_SETTING (
-- ユーザID
USER_ID VARCHAR(16) NOT NULL PRIMARY KEY comment 'ユーザID',
-- ヘッダーフラグ
HEADER_FLAG VARCHAR(1) comment 'ヘッダーフラグ',
-- フッターフラグ
FOOTER_FLAG VARCHAR(1) comment 'フッターフラグ',
-- マスクフラグ
MASK_FLAG VARCHAR(1) comment 'マスクフラグ',
-- 囲い文字利用フラグ
ENCLOSURE_CHAR_FLAG VARCHAR(1) comment '囲い文字利用フラグ',
-- 更新日時
UPDATE_DATE TIMESTAMP comment '更新日時',
-- 登録日時
REG_DATE TIMESTAMP comment '登録日時'
);
