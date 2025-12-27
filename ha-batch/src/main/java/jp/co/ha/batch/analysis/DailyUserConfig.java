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
import jp.co.ha.business.io.file.csv.model.DailyUserCsvModel;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.db.entity.composite.CompositeUser;

/**
 * 日次ユーザ情報データ分析連携バッチConfig<br>
 * <ul>
 * <li>引数1=処理対象年月日(YYYYMMDD)</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Configuration
public class DailyUserConfig extends BatchConfig {

    /** オプション-d */
    private static final String OPTION_D = "d";

    /**
     * 日次ユーザ情報データ分析連携バッチJOB
     * 
     * @param jobRepository
     *     JobRepository
     * @param dailyUserStep
     *     日次ユーザ情報データ分析連携バッチSTEP
     * @param listener
     *     BatchJobListener
     * @return 日次ユーザ情報データ分析連携バッチJOB
     */
    @Bean(DAILY_USER_JOB_NAME)
    Job dailyUserJob(JobRepository jobRepository,
            @Qualifier(DAILY_USER_STEP_NAME) Step dailyUserStep,
            BatchJobListener listener) {
        return new JobBuilder(DAILY_USER_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new DateFormatParameterValidator(OPTION_D,
                        DateFormatType.YYYYMMDD_NOSEP, false))
                .listener(listener)
                .start(dailyUserStep)
                .build();
    }

    /**
     * 日次ユーザ情報データ分析連携バッチSTEP
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
     * @return 日次ユーザ情報データ分析連携バッチSTEP
     */
    @Bean(DAILY_USER_STEP_NAME)
    Step dailyUserStep(
            DailyUserReader reader,
            DailyUserProcessor processor,
            DailyUserWriter writer,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(DAILY_USER_STEP_NAME, jobRepository)
                .<CompositeUser, DailyUserCsvModel> chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
