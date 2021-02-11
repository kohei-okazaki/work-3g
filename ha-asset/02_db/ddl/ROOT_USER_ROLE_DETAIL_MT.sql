-- 管理者サイトユーザ権限詳細マスタ
CREATE TABLE IF NOT EXISTS ROOT_USER_ROLE_DETAIL_MT (
  -- 管理者サイトユーザ権限詳細マスタID
  SEQ_ROOT_USER_ROLE_DETAIL_MT_ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '管理者サイトユーザ権限詳細マスタID',
  -- 管理者サイトユーザ権限管理マスタID
  SEQ_ROOT_USER_ROLE_MT_ID INT NOT NULL COMMENT '管理者サイトユーザ権限管理マスタID',
  -- 管理者サイト権限マスタID
  SEQ_ROOT_ROLE_MT_ID INT NOT NULL COMMENT '管理者サイト権限マスタID',
  -- 更新日時
  UPDATE_DATE DATETIME NOT NULL COMMENT '更新日時',
  -- 登録日時
  REG_DATE DATETIME NOT NULL COMMENT '登録日時'
);