-- Spring Batch 6の採番テーブル名へ変更し、既存の採番値を引き継ぐ。
RENAME TABLE BATCH_JOB_SEQ TO BATCH_JOB_INSTANCE_SEQ;
