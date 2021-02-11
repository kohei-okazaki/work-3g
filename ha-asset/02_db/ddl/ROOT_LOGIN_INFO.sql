-- 管理者サイトユーザログイン情報
CREATE TABLE IF NOT EXISTS ROOT_LOGIN_INFO (
  -- 管理者サイトユーザログイン情報ID
  SEQ_ROOT_LOGIN_INFO_ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '管理者サイトユーザログイン情報ID',
  -- パスワード
  PASSWORD VARCHAR(64) NOT NULL COMMENT 'パスワード',
  -- 削除フラグ
  DELETE_FLAG VARCHAR(1) NOT NULL COMMENT '削除フラグ',
  -- パスワード有効期限
  PASSWORD_EXPIRE DATE NOT NULL COMMENT 'パスワード有効期限',
  -- 管理者サイトユーザ権限管理マスタID
  SEQ_ROOT_USER_ROLE_MT_ID INT NOT NULL COMMENT '管理者サイトユーザ権限管理マスタID',
  -- 備考
  REMARKS VARCHAR(256) COMMENT '備考',
  -- 更新日時
  UPDATE_DATE DATETIME NOT NULL COMMENT '更新日時',
  -- 登録日時
  REG_DATE DATETIME NOT NULL COMMENT '登録日時'
);