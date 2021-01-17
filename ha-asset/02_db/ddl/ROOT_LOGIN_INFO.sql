-- 管理者サイトユーザログイン情報
CREATE TABLE IF NOT EXISTS ROOT_LOGIN_INFO (
  -- 管理者サイトユーザログイン情報ID
  SEQ_ROOT_LOGIN_INFO_ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '管理者サイトユーザログイン情報ID',
  -- パスワード
  PASSWORD VARCHAR(64) COMMENT 'パスワード',
  -- 削除フラグ
  DELETE_FLAG VARCHAR(1) NOT NULL COMMENT '削除フラグ',
  -- パスワード有効期限
  PASSWORD_EXPIRE DATE NOT NULL COMMENT 'パスワード有効期限',
  -- ユーザ権限
  ROLE VARCHAR(2) COMMENT 'ユーザ権限',
  -- 備考
  REMARKS VARCHAR(256) COMMENT '備考',
  -- 更新日時
  UPDATE_DATE DATETIME NOT NULL COMMENT '更新日時',
  -- 登録日時
  REG_DATE DATETIME NOT NULL COMMENT '登録日時'
);