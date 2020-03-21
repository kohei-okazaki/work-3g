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
