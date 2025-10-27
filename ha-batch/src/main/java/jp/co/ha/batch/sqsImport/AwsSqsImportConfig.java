package jp.co.ha.batch.sqsImport;

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
 * AWS SQS取込バッチのConfig
 * 
 * @version 1.0.0
 */
@Configuration
public class AwsSqsImportConfig extends BatchConfig {

    /** AWS SQS取込-Tasklet */
    @Autowired
    private ApiCommunicationDataSqsImportTasklet awsSqsImportTasklet;

    /**
     * AWS SQS取込バッチJOB
     * 
     * @param jobRepository
     *     JobRepository
     * @param queueImportStep
     *     AWS SQS取込STEP
     * @param listener
     *     BatchJobListener
     * @return AWS SQS取込バッチJOB
     */
    @Bean(AWS_SQS_IMPORT_BATCH_JOB_NAME)
    Job awsSqsImportBatchJob(JobRepository jobRepository,
            @Qualifier(AWS_SQS_IMPORT_BATCH_STEP_NAME) Step queueImportStep,
            BatchJobListener listener) {
        return new JobBuilder(AWS_SQS_IMPORT_BATCH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(queueImportStep)
                .build();
    }

    /**
     * AWS SQS取込STEP
     * 
     * @param jobRepository
     *     jobRepository
     * @param transactionManager
     *     transactionManager
     * @return AWS SQS取込STEP
     */
    @Bean(AWS_SQS_IMPORT_BATCH_STEP_NAME)
    Step awsSqsImportBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(AWS_SQS_IMPORT_BATCH_STEP_NAME, jobRepository)
                .tasklet(awsSqsImportTasklet, transactionManager)
                .build();
    }
}
