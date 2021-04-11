-- 管理者サイトユーザメモ情報
CREATE TABLE IF NOT EXISTS ROOT_USER_NOTE_INFO (
  -- メモユーザ情報ID
  SEQ_ROOT_USER_NOTE_INFO_ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT 'メモユーザ情報ID',
  -- 管理者サイトユーザログイン情報ID
  SEQ_ROOT_LOGIN_INFO_ID BIGINT NOT NULL COMMENT '管理者サイトユーザログイン情報ID',
  -- 件名
  TITLE VARCHAR(30) NOT NULL COMMENT '件名',
  -- S3キー
  S3_KEY VARCHAR(100) NOT NULL COMMENT 'S3キー',
  -- 更新日時
  UPDATE_DATE DATETIME NOT NULL COMMENT '更新日時',
  -- 登録日時
  REG_DATE DATETIME NOT NULL COMMENT '登録日時'
);