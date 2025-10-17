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
import org.springframework.transaction.PlatformTransactionManager;

import jp.co.ha.batch.healthInfoMigrateBatch.HealthInfoMigrateBatch;
import jp.co.ha.batch.healthInfoMigrateBatch.HealthInfoMigrateValidator;
import jp.co.ha.batch.listener.BatchJobListener;

/**
 * 健康情報連携バッチのConfig
 * 
 * @version 1.0.0
 */
@Configuration
public class HealthInfoMigrateConfig extends BatchConfig {

    /** 健康情報連携バッチ */
    @Autowired
    private HealthInfoMigrateBatch healthInfoMigrateBatch;

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
