ALTER TABLE API_COMMUNICATION_DATA DROP SEQ_USER_ID;
ALTER TABLE API_COMMUNICATION_DATA DROP RESULT;

ALTER TABLE API_COMMUNICATION_DATA ADD HTTP_METHOD VARCHAR(16);
ALTER TABLE API_COMMUNICATION_DATA ADD URL VARCHAR(256);
ALTER TABLE API_COMMUNICATION_DATA ADD BODY VARCHAR(1024);
