package jp.co.ha.batch.datapurge;

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
 * データパージバッチConfig
 * 
 * @version 1.0.0
 */
@Configuration
public class DataPurgeConfig extends BatchConfig {

    /** データパージ処理-tasklet */
    @Autowired
    private DataPurgeTasklet dataPurgeTasklet;

    /**
     * データパージバッチJOB
     * 
     * @param jobRepository
     *     JobRepository
     * @param queueImportStep
     *     AWS SQS取込STEP
     * @param listener
     *     BatchJobListener
     * @return データパージバッチJOB
     */
    @Bean(DATA_PURGE_BACTH_JOB_NAME)
    Job awsSqsImportBatchJob(JobRepository jobRepository,
            @Qualifier(DATA_PURGE_BACTH_STEP_NAME) Step queueImportStep,
            BatchJobListener listener) {
        return new JobBuilder(DATA_PURGE_BACTH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(queueImportStep)
                .build();
    }

    /**
     * データパージバッチSTEP
     * 
     * @param jobRepository
     *     jobRepository
     * @param transactionManager
     *     transactionManager
     * @return データパージバッチSTEP
     */
    @Bean(DATA_PURGE_BACTH_STEP_NAME)
    Step awsSqsImportBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(AWS_SQS_IMPORT_BATCH_STEP_NAME, jobRepository)
                .tasklet(dataPurgeTasklet, transactionManager)
                .build();
    }

}
