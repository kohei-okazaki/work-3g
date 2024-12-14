package jp.co.ha.batch.config;

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
    @Bean("healthCheckBatchJob")
    Job healthCheckBatchJob(JobRepository jobRepository,
            @Qualifier("healthCheckBatchStep") Step healthCheckBatchStep,
            BatchJobListener listener) {
        return new JobBuilder("healthCheckBatchJob", jobRepository)
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
    @Bean("healthCheckBatchStep")
    Step healthCheckBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder("healthCheckBatchStep", jobRepository)
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
    @Bean("healthInfoFileRegistBatchJob")
    Job healthInfoFileRegistBatchJob(JobRepository jobRepository,
            @Qualifier("healthInfoFileRegistBatchStep") Step healthInfoFileRegistBatchStep,
            BatchJobListener listener) {
        return new JobBuilder("healthInfoFileRegistBatchJob", jobRepository)
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
    @Bean("healthInfoFileRegistBatchStep")
    Step healthInfoFileRegistBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder("healthInfoFileRegistBatchStep", jobRepository)
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
    @Bean("monthlyHealthInfoSummaryBatchJob")
    Job monthlyHealthInfoSummaryBatchJob(JobRepository jobRepository,
            @Qualifier("monthlyHealthInfoSummaryBatchStep") Step monthlyHealthInfoSummaryBatchStep,
            BatchJobListener listener) {
        return new JobBuilder("monthlyHealthInfoSummaryBatchJob", jobRepository)
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
    @Bean("monthlyHealthInfoSummaryBatchStep")
    Step monthlyHealthInfoSummaryBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder("monthlyHealthInfoSummaryBatchStep", jobRepository)
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
    @Bean("healthInfoMigrateBatchJob")
    Job healthInfoMigrateBatchJob(JobRepository jobRepository,
            @Qualifier("healthInfoMigrateBatchStep") Step healthInfoMigrateBatchStep,
            BatchJobListener listener) {
        return new JobBuilder("healthInfoMigrateBatchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
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
    @Bean("healthInfoMigrateBatchStep")
    Step healthInfoMigrateBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder("healthInfoMigrateBatchStep", jobRepository)
                .tasklet(healthInfoMigrateBatch, transactionManager)
                .build();
    }

}
