CREATE TABLE ACCOUNT (

USER_ID VARCHAR(16) NOT NULL PRIMARY KEY,

PASSWORD VARCHAR(16),

DELETE_FLAG VARCHAR(1),

PASSWORD_EXPIRE DATE,

REMARKS VARCHAR(256),

API_KEY VARCHAR(64)

UPDATE_DATE TIMESTAMP,

REG_DATE TIMESTAMP

);