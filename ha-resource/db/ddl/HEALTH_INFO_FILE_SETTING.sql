CREATE TABLE HEALTH_INFO_FILE_SETTING (
USER_ID VARCHAR(16) NOT NULL PRIMARY KEY,

HEADER_FLAG VARCHAR(1),

FOOTER_FLAG VARCHAR(1),

MASK_FLAG VARCHAR(1),

ENCLOSURE_CHAR_FLAG VARCHAR(1),

UPDATE_DATE TIMESTAMP,

REG_DATE TIMESTAMP
);
