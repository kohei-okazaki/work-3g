package jp.co.ha.batch.healthInfoFileRegist;

import static jp.co.ha.batch.base.BatchConfigConst.*;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import jp.co.ha.batch.base.BatchConfig;
import jp.co.ha.batch.listener.BatchJobListener;
import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;

/**
 * 健康情報一括登録バッチのConfig
 * 
 * @version 1.0.0
 */
@Configuration
public class HealthInfoFileRegistConfig extends BatchConfig {

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
     * 健康情報一括登録バッチSTEP
     * 
     * @param reader
     *     健康情報一括登録-Reader
     * @param processor
     *     健康情報一括登録-Proccesor
     * @param writer
     *     健康情報一括登録-Writer
     * @param jobRepository
     *     JobRepository
     * @param transactionManager
     *     PlatformTransactionManager
     * @return 健康情報一括登録バッチSTEP
     */
    @Bean(HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME)
    Step monthlyHealthInfoSummaryStep(
            HealthInfoFileRegistReader reader,
            HealthInfoFileRegistProcessor processor,
            HealthInfoFileRegistWriter writer,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(HEALTH_INFO_FILE_REGIST_BATCH_STEP_NAME, jobRepository)
                .<HealthInfoRegistApiRequest, HealthInfoRegistApiRequest> chunk(1,
                        transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retry(RestClientException.class)
                .retryLimit(3)
                .skip(HttpClientErrorException.class)
                .skipLimit(10)
                .build();
    }

}
