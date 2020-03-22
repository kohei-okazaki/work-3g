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