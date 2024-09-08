-- springbatchのメタテーブルを一度drop
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_INSTANCE;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION;
SET foreign_key_checks = 1;

-- springbatchのメタテーブルcreate
CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION_SEQ (ID BIGINT NOT NULL);
CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_SEQ (ID BIGINT NOT NULL);
CREATE TABLE IF NOT EXISTS BATCH_JOB_SEQ (ID BIGINT NOT NULL);

INSERT INTO BATCH_JOB_EXECUTION_SEQ VALUES (0);
INSERT INTO BATCH_STEP_EXECUTION_SEQ VALUES (0);
INSERT INTO BATCH_JOB_SEQ VALUES (0);

CREATE TABLE IF NOT EXISTS BATCH_JOB_INSTANCE  (
  JOB_INSTANCE_ID BIGINT  PRIMARY KEY ,
  VERSION BIGINT,
  JOB_NAME VARCHAR(100) NOT NULL ,
  JOB_KEY VARCHAR(2500)
);

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION  (
  JOB_EXECUTION_ID BIGINT  PRIMARY KEY ,
  VERSION BIGINT,
  JOB_INSTANCE_ID BIGINT NOT NULL,
  CREATE_TIME TIMESTAMP NOT NULL,
  START_TIME TIMESTAMP DEFAULT NULL,
  END_TIME TIMESTAMP DEFAULT NULL,
  STATUS VARCHAR(10),
  EXIT_CODE VARCHAR(20),
  EXIT_MESSAGE VARCHAR(2500),
  LAST_UPDATED TIMESTAMP,
  JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
  CONSTRAINT JOB_INSTANCE_EXECUTION_FK
  FOREIGN KEY (JOB_INSTANCE_ID)
  REFERENCES BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
);

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	PARAMETER_NAME VARCHAR(100) NOT NULL ,
	PARAMETER_TYPE VARCHAR(100) NOT NULL ,
	PARAMETER_VALUE VARCHAR(2500) ,
	IDENTIFYING CHAR(1) NOT NULL ,
	CONSTRAINT JOB_EXEC_PARAMS_FK
	FOREIGN KEY (JOB_EXECUTION_ID)
	REFERENCES BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_CONTEXT  (
  JOB_EXECUTION_ID BIGINT PRIMARY KEY,
  SHORT_CONTEXT VARCHAR(2500) NOT NULL,
  SERIALIZED_CONTEXT BLOB,
  CONSTRAINT JOB_EXEC_CTX_FK
  FOREIGN KEY (JOB_EXECUTION_ID)
  REFERENCES BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION  (
  STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY ,
  VERSION BIGINT NOT NULL,
  STEP_NAME VARCHAR(100) NOT NULL,
  JOB_EXECUTION_ID BIGINT NOT NULL,
  CREATE_TIME TIMESTAMP NOT NULL,
  START_TIME TIMESTAMP DEFAULT NULL ,
  END_TIME TIMESTAMP DEFAULT NULL,
  STATUS VARCHAR(10),
  COMMIT_COUNT BIGINT ,
  READ_COUNT BIGINT ,
  FILTER_COUNT BIGINT ,
  WRITE_COUNT BIGINT ,
  READ_SKIP_COUNT BIGINT ,
  WRITE_SKIP_COUNT BIGINT ,
  PROCESS_SKIP_COUNT BIGINT ,
  ROLLBACK_COUNT BIGINT ,
  EXIT_CODE VARCHAR(20) ,
  EXIT_MESSAGE VARCHAR(2500) ,
  LAST_UPDATED TIMESTAMP,
  CONSTRAINT JOB_EXECUTION_STEP_FK
  FOREIGN KEY (JOB_EXECUTION_ID)
  REFERENCES BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION_CONTEXT  (
  STEP_EXECUTION_ID BIGINT PRIMARY KEY,
  SHORT_CONTEXT VARCHAR(2500) NOT NULL,
  SERIALIZED_CONTEXT BLOB,
  CONSTRAINT STEP_EXEC_CTX_FK
  FOREIGN KEY (STEP_EXECUTION_ID)
  REFERENCES BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;
