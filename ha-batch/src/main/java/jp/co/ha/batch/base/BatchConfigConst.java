package jp.co.ha.batch.base;

/**
 * 健康管理バッチのジョブ設定定数
 * 
 * @version 1.0.0
 */
public class BatchConfigConst {

    /** JOB名 - ヘルスチェックバッチ */
    public static final String HEALTH_CHECK_BACTH_JOB_NAME = "healthCheckBatchJob";
    /** STEP名 - 健康管理APIヘルスチェックStep */
    public static final String HEALTH_CHECK_APP_API_STEP_NAME = "appApiHealthCheckStep";
    /** STEP名 - 管理者用APIヘルスチェックStep */
    public static final String HEALTH_CHECK_ROOT_API_STEP_NAME = "rootApihealthCheckStep";
    /** STEP名 - ヘルスチェック通知用Step */
    public static final String HEALTH_CHECK_NOTIFY_STEP_NAME = "healthCheckNotifyStep";
    /** FLOW名 - 健康管理APIヘルスチェックFlow */
    public static final String HEALTH_CHECK_APP_API_FLOW_NAME = "appApiHealthCheckFlow";
    /** FLOW名 - 管理者用APIヘルスチェックFlow */
    public static final String HEALTH_CHECK_ROOT_API_FLOW_NAME = "rootApiHealthCheckFlow";

    /** JOB名 - 健康情報一括登録バッチ */
    public static final String HEALTH_INFO_FILE_REGIST_BATCH_JOB_NAME = "healthInfoFileRegistBatchJob";
    /** STEP名 - 健康情報一括登録バッチ */
    public static final String HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME = "healthInfoFileRegistBatchStep";

    /** JOB名 - 月次健康情報集計バッチ */
    public static final String MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME = "monthlyHealthInfoSummaryBatchJob";
    /** STEP名 - 月次健康情報集計Step */
    public static final String MONTHLY_HEALTH_INFO_SUMMARY_BACTH_STEP_NAME = "monthlyHealthInfoSummaryBatchStep";

    /** JOB名 - 健康情報連携バッチ */
    public static final String HEALTH_INFO_MIGRATE_BATCH_JOB_NAME = "healthInfoMigrateBatchJob";
    /** STEP名 - 健康情報連携バッチ */
    public static final String HEALTH_INFO_MIGRATE_BATCH_STEP_NAME = "healthInfoMigrateBatchStep";

    /** JOB名 - AWS SQS取込バッチ */
    public static final String AWS_SQS_IMPORT_BATCH_JOB_NAME = "awsSqsImportBatchJob";
    /** STEP名 - AWS SQS取込バッチ */
    public static final String AWS_SQS_IMPORT_BATCH_STEP_NAME = "awsSqsImportBatchStep";

    /** JOB名 - データパージバッチ */
    public static final String DATA_PURGE_BACTH_JOB_NAME = "dataPurgeBatchJob";
    /** STEP名 - データパージバッチ */
    public static final String DATA_PURGE_BACTH_STEP_NAME = "dataPurgeBatchStep";

    /** JOB名 - 日次健康情報データ分析連携バッチ */
    public static final String DAILY_HEALTH_INFO_JOB_NAME = "dailyHealthInfoJob";
    /** STEP名 - 日次健康情報データ分析連携バッチ */
    public static final String DAILY_HEALTH_INFO_STEP_NAME = "dailyHealthInfoStep";
    /** JOB名 - 日次ユーザ情報データ分析連携バッチ */
    public static final String DAILY_USER_INFO_JOB_NAME = "dailyUserInfoJob";
    /** STEP名 - 日次ユーザ情報データ分析連携バッチ */
    public static final String DAILY_USER_INFO_STEP_NAME = "dailyUserInfoStep";

}
