package jp.co.ha.batch.healthInfoFileRegist;

import static jp.co.ha.batch.base.BatchConfigConst.*;

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

import jp.co.ha.batch.base.BatchConfig;
import jp.co.ha.batch.listener.BatchJobListener;

/**
 * 健康情報一括登録バッチのConfig
 * 
 * @version 1.0.0
 */
@Configuration
public class HealthInfoFileRegistConfig extends BatchConfig {

    /** 健康情報一括登録Tasklet */
    @Autowired
    private HealthInfoFileRegistTasklet healthInfoFileRegistTasklet;

    /**
     * 健康情報一括登録バッチJOB
     *
     * @param jobRepository
     *     JobRepository
     * @param healthInfoFileRegistBatchStep
     *     健康情報一括登録STEP
     * @param listener
     *     BatchJobListener
     * @return 健康情報一括登録バッチJOB
     */
    @Bean(HEALTH_INFO_FILE_REGIST_BATCH_JOB_NAME)
    Job healthInfoFileRegistBatchJob(JobRepository jobRepository,
            @Qualifier(HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME) Step healthInfoFileRegistBatchStep,
            BatchJobListener listener) {
        return new JobBuilder(HEALTH_INFO_FILE_REGIST_BATCH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(healthInfoFileRegistBatchStep)
                .build();

    }

    /**
     * 健康情報一括登録STEP
     *
     * @param jobRepository
     *     JobRepository
     * @param transactionManager
     *     PlatformTransactionManager
     * @return 健康情報一括登録バッチのSTEP
     */
    @Bean(HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME)
    Step healthInfoFileRegistBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME,
                jobRepository)
                        .tasklet(healthInfoFileRegistTasklet, transactionManager)
                        .build();
    }
}
