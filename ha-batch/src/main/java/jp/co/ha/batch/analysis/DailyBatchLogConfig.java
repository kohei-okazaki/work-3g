package jp.co.ha.batch.analysis;

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

import jp.co.ha.batch.base.BatchConfig;
import jp.co.ha.batch.base.DateFormatParameterValidator;
import jp.co.ha.batch.listener.BatchJobListener;
import jp.co.ha.business.io.file.csv.model.DailyBatchLogCsvModel;
import jp.co.ha.db.entity.custom.CustomJobData;

/**
 * 日次バッチ起動ログデータ分析連携バッチConfig<br>
 * <ul>
 * <li>引数1=処理対象日(YYYYMMDD)</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Configuration
public class DailyBatchLogConfig extends BatchConfig {

    /** オプション-d */
    private static final String OPTION_D = "d";

    /**
     * 日次バッチ起動ログデータ分析連携バッチJOB
     * 
     * @param jobRepository
     *     JobRepository
     * @param dailyBatchLogStep
     *     日次バッチ起動ログデータ分析連携バッチSTEP
     * @param listener
     *     BatchJobListener
     * @return 日次バッチ起動ログデータ分析連携バッチJOB
     */
    @Bean(DAILY_BACTH_LOG_JOB_NAME)
    Job dailyBatchLogJob(JobRepository jobRepository,
            @Qualifier(DAILY_BACTH_LOG_STEP_NAME) Step dailyBatchLogStep,
            BatchJobListener listener) {
        return new JobBuilder(DAILY_BACTH_LOG_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(
                        new DateFormatParameterValidator(OPTION_D, YYYYMMDD_NOSEP, false))
                .listener(listener)
                .start(dailyBatchLogStep)
                .build();
    }

    /**
     * 日次バッチ起動ログデータ分析連携バッチSTEP
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
     * @return 日次バッチ起動ログデータ分析連携バッチSTEP
     */
    @Bean(DAILY_BACTH_LOG_STEP_NAME)
    Step dailyBatchLogStep(
            DailyBatchLogReader reader,
            DailyBatchLogProcessor processor,
            DailyBatchLogWriter writer,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(DAILY_BACTH_LOG_STEP_NAME, jobRepository)
                .<CustomJobData, DailyBatchLogCsvModel> chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
