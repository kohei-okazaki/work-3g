-- アカウント情報
CREATE TABLE IF NOT EXISTS ACCOUNT (
  -- ユーザID
  USER_ID VARCHAR(16) NOT NULL PRIMARY KEY COMMENT 'ユーザID',
  -- パスワード
  PASSWORD VARBINARY(176) NOT NULL COMMENT 'パスワード',
  -- 削除フラグ
  DELETE_FLAG VARCHAR(1) NOT NULL COMMENT '削除フラグ',
  -- パスワード有効期限
  PASSWORD_EXPIRE DATE NOT NULL COMMENT 'パスワード有効期限',
  -- 備考
  REMARKS VARCHAR(256) COMMENT '備考',
  -- APIキー
  API_KEY VARCHAR(64) COMMENT 'APIキー',
  -- 更新日時
  UPDATE_DATE TIMESTAMP COMMENT '更新日時',
  -- 登録日時
  REG_DATE TIMESTAMP COMMENT '登録日時'
);

-- BMI範囲マスタ
CREATE TABLE IF NOT EXISTS BMI_RANGE_MT (
  -- BMI範囲ID
  BMI_RANGE_ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT 'BMI範囲ID',
  -- 範囲下限
  RANGE_MIN INT COMMENT '範囲下限',
  -- 範囲上限
  RANGE_MAX INT COMMENT '範囲上限',
  -- 肥満度ステータス
  OVER_WEIGHT_STATUS VARCHAR(2) COMMENT '肥満度ステータス',
  -- 更新日時
  UPDATE_DATE TIMESTAMP COMMENT '更新日時',
  -- 登録日時
  REG_DATE TIMESTAMP COMMENT '登録日時'
);

-- 健康情報ファイル設定
CREATE TABLE IF NOT EXISTS HEALTH_INFO_FILE_SETTING (
  -- ユーザID
  USER_ID VARCHAR(16) NOT NULL PRIMARY KEY COMMENT 'ユーザID',
  -- ヘッダーフラグ
  HEADER_FLAG VARCHAR(1) COMMENT 'ヘッダーフラグ',
  -- フッターフラグ
  FOOTER_FLAG VARCHAR(1) COMMENT 'フッターフラグ',
  -- マスクフラグ
  MASK_FLAG VARCHAR(1) COMMENT 'マスクフラグ',
  -- 囲い文字利用フラグ
  ENCLOSURE_CHAR_FLAG VARCHAR(1) COMMENT '囲い文字利用フラグ',
  -- 更新日時
  UPDATE_DATE TIMESTAMP COMMENT '更新日時',
  -- 登録日時
  REG_DATE TIMESTAMP COMMENT '登録日時'
);

-- 健康情報
CREATE TABLE IF NOT EXISTS HEALTH_INFO (
  -- 健康情報ID
  HEALTH_INFO_ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '健康情報ID',
  -- ユーザID
  USER_ID VARCHAR(16) NOT NULL COMMENT 'ユーザID',
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
  HEALTH_INFO_REG_DATE TIMESTAMP COMMENT '健康情報作成日時',
  -- BMI範囲ID
  BMI_RANGE_ID INT COMMENT 'BMI範囲ID',
  -- 更新日時
  UPDATE_DATE TIMESTAMP COMMENT '更新日時',
  -- 登録日時
  REG_DATE TIMESTAMP COMMENT '登録日時'
);

-- メール情報
CREATE TABLE IF NOT EXISTS MAIL_INFO (
  -- ユーザID
  USER_ID VARCHAR(16) NOT NULL PRIMARY KEY COMMENT 'ユーザID',
  -- メールアドレス
  MAIL_ADDRESS VARBINARY(432) NOT NULL COMMENT 'メールアドレス',
  -- メールパスワード
  MAIL_PASSWORD VARBINARY(176) NOT NULL COMMENT 'メールパスワード',
  -- 更新日時
  UPDATE_DATE TIMESTAMP COMMENT '更新日時',
  -- 登録日時
  REG_DATE TIMESTAMP COMMENT '登録日時'
);

-- BMI_RANGE_MT
INSERT INTO BMI_RANGE_MT (
  RANGE_MIN, RANGE_MAX, OVER_WEIGHT_STATUS, UPDATE_DATE, REG_DATE
) VALUES (
  0, 18, 10, now(), now()
);

INSERT INTO BMI_RANGE_MT (
  RANGE_MIN, RANGE_MAX, OVER_WEIGHT_STATUS, UPDATE_DATE, REG_DATE
) VALUES (
  18, 25, 11, now(), now()
);

INSERT INTO BMI_RANGE_MT (
  RANGE_MIN, RANGE_MAX, OVER_WEIGHT_STATUS, UPDATE_DATE, REG_DATE
) VALUES (
  25, 30, 12, now(), now()
);

INSERT INTO BMI_RANGE_MT (
  RANGE_MIN, RANGE_MAX, OVER_WEIGHT_STATUS, UPDATE_DATE, REG_DATE
) VALUES (
  30, 35, 13, now(), now()
);

INSERT INTO BMI_RANGE_MT (
  RANGE_MIN, RANGE_MAX, OVER_WEIGHT_STATUS, UPDATE_DATE, REG_DATE
) VALUES (
  35, 40, 14, now(), now()
);

INSERT INTO BMI_RANGE_MT (
  RANGE_MIN, RANGE_MAX, OVER_WEIGHT_STATUS, UPDATE_DATE, REG_DATE
) VALUES (
  40, 999, 15, now(), now()
);
