-- ユーザの作成
CREATE USER 'app_user'@'localhost3306' IDENTIFIED BY '3w4tamudnxgr4';
-- ユーザ権限の設定
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP, INDEX ON work3g.* TO 'app_user'@'localhost';
FLUSH PRIVILEGES;
