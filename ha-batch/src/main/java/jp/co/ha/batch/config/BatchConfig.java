package jp.co.ha.batch.config;

import static jp.co.ha.batch.config.BatchConfigConst.*;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import jp.co.ha.batch.healthInfoFileRegist.HealthInfoFileRegistBatch;
import jp.co.ha.batch.healthInfoMigrateBatch.HealthInfoMigrateBatch;
import jp.co.ha.batch.healthInfoMigrateBatch.HealthInfoMigrateValidator;
import jp.co.ha.batch.healthcheck.HealthCheckBatch;
import jp.co.ha.batch.listener.BatchJobListener;
import jp.co.ha.batch.monthlyHealthInfoSummary.MonthlyHealthInfoSummaryBatch;
import jp.co.ha.batch.monthlyHealthInfoSummary.MonthlyHealthInfoSummaryValidator;
import jp.co.ha.business.config.BusinessConfig;
import jp.co.ha.common.config.CommonConfig;
import jp.co.ha.db.config.DbConfig;

/**
 * Batch処理の定義クラス
 *
 * @version 1.0.0
 */
@Configuration
// commonプロジェクトなどのbean定義を読込
@Import({
        CommonConfig.class,
        DbConfig.class,
        BusinessConfig.class
})
public class BatchConfig {

    /** {@linkplain HealthCheckBatch} */
    @Autowired
    private HealthCheckBatch healthCheckBatch;
    /** {@linkplain HealthInfoFileRegistBatch} */
    @Autowired
    private HealthInfoFileRegistBatch healthInfoFileRegistBatch;
    /** {@linkplain HealthInfoFileRegistBatch} */
    @Autowired
    private MonthlyHealthInfoSummaryBatch monthlyHealthInfoSummaryBatch;
    /** {@linkplain HealthInfoMigrateBatch} */
    @Autowired
    private HealthInfoMigrateBatch healthInfoMigrateBatch;

    /**
     * ヘルスチェックバッチのJOB<br>
     * healthCheckBatchJob
     * 
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param healthCheckBatchStep
     *     ヘルスチェックバッチのSTEP
     * @param listener
     *     {@linkplain BatchJobListener}
     * @return ヘルスチェックバッチJOB
     */
    @Bean(HEALTH_CHECK_BACTH_JOB_NAME)
    Job healthCheckBatchJob(JobRepository jobRepository,
            @Qualifier(HEALTH_CHECK_BACTH_STEP_NAME) Step healthCheckBatchStep,
            BatchJobListener listener) {
        return new JobBuilder(HEALTH_CHECK_BACTH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(healthCheckBatchStep)
                .end()
                .build();
    }

    /**
     * ヘルスチェックバッチのSTEP<br>
     * healthCheckBatchStep
     * 
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param transactionManager
     *     {@linkplain PlatformTransactionManager}
     * @return ヘルスチェックバッチのSTEP
     */
    @Bean(HEALTH_CHECK_BACTH_STEP_NAME)
    Step healthCheckBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(HEALTH_CHECK_BACTH_STEP_NAME, jobRepository)
                .tasklet(healthCheckBatch, transactionManager)
                .build();
    }

    /**
     * 健康情報一括登録バッチのJOB<br>
     * healthInfoFileRegistBatchJob
     *
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param healthInfoFileRegistBatchStep
     *     健康情報一括登録バッチのSTEP
     * @param listener
     *     {@linkplain BatchJobListener}
     * @return 健康情報一括登録バッチJOB
     */
    @Bean(HEALTH_INFO_FILE_REGIST_BATCH_JOB_NAME)
    Job healthInfoFileRegistBatchJob(JobRepository jobRepository,
            @Qualifier(HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME) Step healthInfoFileRegistBatchStep,
            BatchJobListener listener) {
        return new JobBuilder(HEALTH_INFO_FILE_REGIST_BATCH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(healthInfoFileRegistBatchStep)
                .end()
                .build();

    }

    /**
     * 健康情報一括登録バッチのSTEP<br>
     * healthInfoFileRegistBatchStep
     *
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param transactionManager
     *     {@linkplain PlatformTransactionManager}
     * @return 健康情報一括登録バッチのSTEP
     */
    @Bean(HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME)
    Step healthInfoFileRegistBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME,
                jobRepository)
                        .tasklet(healthInfoFileRegistBatch, transactionManager)
                        .build();
    }

    /**
     * 月次健康情報集計バッチのJOB<br>
     * monthlyHealthInfoSummaryBatchJob
     * 
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param monthlyHealthInfoSummaryBatchStep
     *     月次健康情報集計バッチのSTEP
     * @param listener
     *     {@linkplain BatchJobListener}
     * @return 月次健康情報集計バッチJOB
     */
    @Bean(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME)
    Job monthlyHealthInfoSummaryBatchJob(JobRepository jobRepository,
            @Qualifier(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_STEP_NAME) Step monthlyHealthInfoSummaryBatchStep,
            BatchJobListener listener) {
        return new JobBuilder(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new MonthlyHealthInfoSummaryValidator())
                .listener(listener)
                .flow(monthlyHealthInfoSummaryBatchStep)
                .end()
                .build();
    }

    /**
     * 月次健康情報集計バッチのSTEP<br>
     * monthlyHealthInfoSummaryBatchStep
     * 
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param transactionManager
     *     {@linkplain PlatformTransactionManager}
     * @return 月次健康情報集計のSTEP
     */
    @Bean(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_STEP_NAME)
    Step monthlyHealthInfoSummaryBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_STEP_NAME, jobRepository)
                .tasklet(monthlyHealthInfoSummaryBatch, transactionManager)
                .build();
    }

    /**
     * 健康情報連携バッチのJOB
     * 
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param healthInfoMigrateBatchStep
     *     ヘルスチェックバッチのSTEP
     * @param listener
     *     {@linkplain BatchJobListener}
     * @return 健康情報連携バッチJOB
     */
    @Bean(HEALTH_INFO_MIGRATE_BATCH_JOB_NAME)
    Job healthInfoMigrateBatchJob(JobRepository jobRepository,
            @Qualifier(HEALTH_INFO_MIGRATE_BATCH_STEP_NAME) Step healthInfoMigrateBatchStep,
            BatchJobListener listener) {
        return new JobBuilder(HEALTH_INFO_MIGRATE_BATCH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new HealthInfoMigrateValidator())
                .listener(listener)
                .flow(healthInfoMigrateBatchStep)
                .end()
                .build();
    }

    /**
     * 健康情報連携バッチのSTEP<br>
     * 
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param transactionManager
     *     {@linkplain PlatformTransactionManager}
     * @return 健康情報連携バッチのSTEP
     */
    @Bean(HEALTH_INFO_MIGRATE_BATCH_STEP_NAME)
    Step healthInfoMigrateBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(HEALTH_INFO_MIGRATE_BATCH_STEP_NAME, jobRepository)
                .tasklet(healthInfoMigrateBatch, transactionManager)
                .build();
    }

}
