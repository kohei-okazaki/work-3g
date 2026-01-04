package jp.co.ha.batch.healthInfoMigrate;

import static jp.co.ha.batch.base.BatchConfigConst.*;
import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;

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
import jp.co.ha.batch.base.DateFormatParameterValidator;
import jp.co.ha.batch.listener.BatchJobListener;
import jp.co.ha.business.api.track.request.HealthInfoMigrateApiRequest;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報連携バッチConfig<br>
 * <ul>
 * <li>引数1=処理対象年月日(YYYYMMDD)</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Configuration
public class HealthInfoMigrateConfig extends BatchConfig {

    /** オプション-d */
    private static final String OPTION_D = "d";

    /**
     * 健康情報連携バッチJOB
     * 
     * @param jobRepository
     *     JobRepository
     * @param healthInfoMigrateBatchStep
     *     健康情報連携バッチSTEP
     * @param listener
     *     BatchJobListener
     * @return 健康情報連携バッチJOB
     */
    @Bean(HEALTH_INFO_MIGRATE_BATCH_JOB_NAME)
    Job healthInfoMigrateBatchJob(JobRepository jobRepository,
            @Qualifier(HEALTH_INFO_MIGRATE_BATCH_STEP_NAME) Step healthInfoMigrateBatchStep,
            BatchJobListener listener) {
        return new JobBuilder(HEALTH_INFO_MIGRATE_BATCH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(
                        new DateFormatParameterValidator(OPTION_D, YYYYMMDD_NOSEP, false))
                .listener(listener)
                .start(healthInfoMigrateBatchStep)
                .build();
    }

    /**
     * 健康情報連携バッチSTEP
     * 
     * @param reader
     *     Reader
     * @param processor
     *     Proccesor
     * @param writer
     *     Writer
     * @param jobRepository
     *     JobRepository
     * @param transactionManager
     *     PlatformTransactionManager
     * @return 健康情報連携バッチSTEP
     */
    @Bean(HEALTH_INFO_MIGRATE_BATCH_STEP_NAME)
    Step healthInfoMigrateBatchStep(
            HealthInfoMigrateReader reader,
            HealthInfoMigrateProcessor processor,
            HealthInfoMigrateWriter writer,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(HEALTH_INFO_MIGRATE_BATCH_STEP_NAME, jobRepository)
                .<HealthInfo, HealthInfoMigrateApiRequest> chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retry(RestClientException.class)
                .retryLimit(3)
                .skip(HttpClientErrorException.class)
                .skipLimit(10)
                .transactionManager(transactionManager)
                .build();
    }
}
