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

import jp.co.ha.batch.healthcheck.HealthCheckBatch;
import jp.co.ha.batch.listener.BatchJobListener;

/**
 * ヘルスチェックバッチのConfig
 * 
 * @version 1.0.0
 */
@Configuration
public class HealthCheckConfig extends BatchConfig {

    /** ヘルスチェックバッチ */
    @Autowired
    private HealthCheckBatch healthCheckBatch;

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
}
