-- hogeに任意のユーザ名、localhost3306に適切なホスト名、auth_stringに任意のパスワード
CREATE USER 'app_user'@'localhost3306' IDENTIFIED BY '3w4tamudnxgr4';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE ON work3g.* TO 'app_user'@'localhost';
FLUSH PRIVILEGES;
