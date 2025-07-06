package jp.co.ha.batch.config;

/**
 * 健康管理バッチのジョブ設定定数
 * 
 * @version 1.0.0
 */
public class BatchConfigConst {

    /** JOB名 - ヘルスチェックバッチ */
    public static final String HEALTH_CHECK_BACTH_JOB_NAME = "healthCheckBatchJob";
    /** STEP名 - ヘルスチェックバッチ */
    public static final String HEALTH_CHECK_BACTH_STEP_NAME = "healthCheckBatchStep";

    /** JOB名 - 健康情報一括登録バッチ */
    public static final String HEALTH_INFO_FILE_REGIST_BATCH_JOB_NAME = "healthInfoFileRegistBatchJob";
    /** STEP名 - 健康情報一括登録バッチ */
    public static final String HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME = "healthInfoFileRegistBatchStep";

    /** JOB名 - 月次健康情報集計バッチ */
    public static final String MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME = "monthlyHealthInfoSummaryBatchJob";
    /** STEP名 - 月次健康情報集計バッチ */
    public static final String MONTHLY_HEALTH_INFO_SUMMARY_BATCH_STEP_NAME = "monthlyHealthInfoSummaryBatchStep";

    /** JOB名 - 健康情報連携バッチ */
    public static final String HEALTH_INFO_MIGRATE_BATCH_JOB_NAME = "healthInfoMigrateBatchJob";
    /** STEP名 - 健康情報連携バッチ */
    public static final String HEALTH_INFO_MIGRATE_BATCH_STEP_NAME = "healthInfoMigrateBatchStep";

}
