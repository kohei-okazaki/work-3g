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
import jp.co.ha.business.io.file.csv.model.DailyApiLogCsvModel;
import jp.co.ha.db.entity.ApiLog;

/**
 * 日次API通信ログデータ分析連携バッチConfig<br>
 * <ul>
 * <li>引数1=処理対象年月日(YYYYMMDD)</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Configuration
public class DailyApiLogConfig extends BatchConfig {

    /** オプション-d */
    private static final String OPTION_D = "d";

    /**
     * 日次API通信ログデータ分析連携バッチJOB
     * 
     * @param jobRepository
     *     JobRepository
     * @param dailyUserStep
     *     日次API通信ログデータ分析連携バッチSTEP
     * @param listener
     *     BatchJobListener
     * @return 日次API通信ログデータ分析連携バッチJOB
     */
    @Bean(DAILY_API_LOG_JOB_NAME)
    Job dailyApiLogJob(JobRepository jobRepository,
            @Qualifier(DAILY_API_LOG_STEP_NAME) Step dailyUserStep,
            BatchJobListener listener) {
        return new JobBuilder(DAILY_API_LOG_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(
                        new DateFormatParameterValidator(OPTION_D, YYYYMMDD_NOSEP, false))
                .listener(listener)
                .start(dailyUserStep)
                .build();
    }

    /**
     * 日次API通信ログデータ分析連携バッチSTEP
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
     * @return 日次API通信ログデータ分析連携バッチSTEP
     */
    @Bean(DAILY_API_LOG_STEP_NAME)
    Step dailyApiLogStep(
            DailyApiLogReader reader,
            DailyApiLogProcessor processor,
            DailyApiLogWriter writer,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(DAILY_API_LOG_STEP_NAME, jobRepository)
                .<ApiLog, DailyApiLogCsvModel> chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
