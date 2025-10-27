package jp.co.ha.batch.monthlyHealthInfoSummary;

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
 * 月次健康情報集計バッチのConfig<br>
 * <ul>
 * <li>引数1=処理対象年月(YYYYMM)</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Configuration
public class MonthlyHealthInfoSummaryConfig extends BatchConfig {

    /** 月次健康情報集計tasklet */
    @Autowired
    private MonthlyHealthInfoSummaryTasklet monthlyHealthInfoSummaryTasklet;
    /** 月次健康情報アップロード-tasklet */
    @Autowired
    private MonthlyHealthInfoUploadTasklet monthlyHealthInfoUploadTasklet;

    /**
     * 月次健康情報集計バッチのJOB
     * 
     * @param jobRepository
     *     JobRepository
     * @param monthlyHealthInfoSummaryStep
     *     月次健康情報集計バッチのSTEP
     * @param monthlyHealthInfoUploadStep
     *     月次健康情報アップロードのSTEP
     * @param listener
     *     BatchJobListener
     * @return 月次健康情報集計バッチJOB
     */
    @Bean(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME)
    Job monthlyHealthInfoSummaryBatchJob(JobRepository jobRepository,
            @Qualifier(MONTHLY_HEALTH_INFO_SUMMARY_STEP_NAME) Step monthlyHealthInfoSummaryStep,
            @Qualifier(MONTHLY_HEALTH_INFO_UPLOAD_STEP_NAME) Step monthlyHealthInfoUploadStep,
            BatchJobListener listener) {
        return new JobBuilder(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new MonthlyHealthInfoSummaryValidator())
                .listener(listener)
                .start(monthlyHealthInfoSummaryStep)
                .next(monthlyHealthInfoUploadStep)
                .build();
    }

    /**
     * 月次健康情報集計STEP
     * 
     * @param jobRepository
     *     JobRepository
     * @param transactionManager
     *     PlatformTransactionManager
     * @return 月次健康情報集計のSTEP
     */
    @Bean
    Step monthlyHealthInfoSummaryStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(MONTHLY_HEALTH_INFO_SUMMARY_STEP_NAME, jobRepository)
                .tasklet(monthlyHealthInfoSummaryTasklet, transactionManager)
                .build();
    }

    /**
     * 月次健康情報アップロードSTEP
     * 
     * @param jobRepository
     *     JobRepository
     * @param transactionManager
     *     PlatformTransactionManager
     * @return 月次健康情報アップロードSTEP
     */
    @Bean
    Step monthlyHealthInfoUploadStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(MONTHLY_HEALTH_INFO_UPLOAD_STEP_NAME, jobRepository)
                .tasklet(monthlyHealthInfoUploadTasklet, transactionManager)
                .build();
    }
}
