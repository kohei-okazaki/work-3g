-- お知らせ情報
CREATE TABLE IF NOT EXISTS NEWS_INFO (
  -- お知らせ情報ID
  SEQ_NEWS_INFO_ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT 'お知らせ情報ID',
  -- S3キー
  S3_KEY VARCHAR(100) NOT NULL COMMENT 'S3キー',
  -- 更新日時
  UPDATE_DATE DATETIME NOT NULL COMMENT '更新日時',
  -- 登録日時
  REG_DATE DATETIME NOT NULL COMMENT '登録日時'
);
