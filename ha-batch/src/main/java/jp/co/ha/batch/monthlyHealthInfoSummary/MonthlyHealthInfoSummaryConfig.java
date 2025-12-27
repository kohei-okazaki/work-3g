package jp.co.ha.batch.monthlyHealthInfoSummary;

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
import jp.co.ha.business.io.file.csv.model.MonthlyHealthInfoSummaryModel;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 月次健康情報集計バッチConfig<br>
 * <ul>
 * <li>引数1=処理対象年月(YYYYMM)</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Configuration
public class MonthlyHealthInfoSummaryConfig extends BatchConfig {

    /**
     * 月次健康情報集計バッチJOB
     *
     * @param jobRepository
     *     JobRepository
     * @param monthlyHealthInfoSummaryBatchStep
     *     月次健康情報集計バッチSTEP
     * @param listener
     *     BatchJobListener
     * @return 月次健康情報集計バッチJOB
     */
    @Bean(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME)
    Job monthlyHealthInfoSummaryBatchJob(JobRepository jobRepository,
            @Qualifier(MONTHLY_HEALTH_INFO_SUMMARY_BACTH_STEP_NAME) Step monthlyHealthInfoSummaryBatchStep,
            BatchJobListener listener) {
        return new JobBuilder(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new DateFormatParameterValidator("m",
                        DateFormatType.YYYYMM_NOSEP, false))
                .listener(listener)
                .start(monthlyHealthInfoSummaryBatchStep)
                .build();
    }

    /**
     * 月次健康情報集計バッチSTEP
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
     * @return 月次健康情報集計バッチSTEP
     */
    @Bean(MONTHLY_HEALTH_INFO_SUMMARY_BACTH_STEP_NAME)
    Step monthlyHealthInfoSummaryBatchStep(
            MonthlyHealthInfoSummaryReader reader,
            MonthlyHealthInfoSummaryProcessor processor,
            MonthlyHealthInfoSummaryWriter writer,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(MONTHLY_HEALTH_INFO_SUMMARY_BACTH_STEP_NAME, jobRepository)
                .<HealthInfo, MonthlyHealthInfoSummaryModel> chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
