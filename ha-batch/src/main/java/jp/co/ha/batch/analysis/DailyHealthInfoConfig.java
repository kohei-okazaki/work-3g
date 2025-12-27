package jp.co.ha.batch.analysis;

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

import jp.co.ha.batch.base.BatchConfig;
import jp.co.ha.batch.base.DateFormatParameterValidator;
import jp.co.ha.batch.listener.BatchJobListener;
import jp.co.ha.business.io.file.csv.model.DailyHealthInfoCsvModel;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 日次健康情報データ分析連携バッチConfig<br>
 * <ul>
 * <li>引数1=処理対象年月日(YYYYMMDD)</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Configuration
public class DailyHealthInfoConfig extends BatchConfig {

    /**
     * 日次健康情報データ分析連携バッチJOB
     * 
     * @param jobRepository
     *     JobRepository
     * @param dailyHealthInfoMigrateStep
     *     日次健康情報データ分析連携バッチSTEP
     * @param listener
     *     BatchJobListener
     * @return 日次健康情報データ分析連携バッチJOB
     */
    Job dailyHealthInfoMigrateJob(JobRepository jobRepository,
            @Qualifier(DAILY_HEALTH_INFO_STEP_NAME) Step dailyHealthInfoMigrateStep,
            BatchJobListener listener) {
        return new JobBuilder(DAILY_HEALTH_INFO_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new DateFormatParameterValidator("d",
                        DateFormatType.YYYYMMDD_NOSEP, false))
                .listener(listener)
                .start(dailyHealthInfoMigrateStep)
                .build();
    }

    /**
     * 日次健康情報データ分析連携バッチSTEP
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
     * @return 日次健康情報データ分析連携バッチSTEP
     */
    @Bean(DAILY_HEALTH_INFO_STEP_NAME)
    Step dailyHealthInfoMigrateStep(
            DailyHealthInfoReader reader,
            DailyHealthInfoProcessor processor,
            DailyHealthInfoWriter writer,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(DAILY_HEALTH_INFO_STEP_NAME, jobRepository)
                .<HealthInfo, DailyHealthInfoCsvModel> chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
