package jp.co.ha.batch.config;

import static jp.co.ha.batch.config.BatchConfigConst.*;

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

import jp.co.ha.batch.listener.BatchJobListener;
import jp.co.ha.batch.monthlyHealthInfoSummary.MonthlyHealthInfoSummaryBatch;
import jp.co.ha.batch.monthlyHealthInfoSummary.MonthlyHealthInfoSummaryValidator;

/**
 * 月次健康情報集計バッチのConfig
 * 
 * @version 1.0.0
 */
@Configuration
public class MonthlyHealthInfoSummaryConfig extends BatchConfig {

    /** 月次健康情報集計バッチ */
    @Autowired
    private MonthlyHealthInfoSummaryBatch monthlyHealthInfoSummaryBatch;

    /**
     * 月次健康情報集計バッチのJOB<br>
     * monthlyHealthInfoSummaryBatchJob
     * 
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param monthlyHealthInfoSummaryBatchStep
     *     月次健康情報集計バッチのSTEP
     * @param listener
     *     {@linkplain BatchJobListener}
     * @return 月次健康情報集計バッチJOB
     */
    @Bean(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME)
    Job monthlyHealthInfoSummaryBatchJob(JobRepository jobRepository,
            @Qualifier(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_STEP_NAME) Step monthlyHealthInfoSummaryBatchStep,
            BatchJobListener listener) {
        return new JobBuilder(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new MonthlyHealthInfoSummaryValidator())
                .listener(listener)
                .flow(monthlyHealthInfoSummaryBatchStep)
                .end()
                .build();
    }

    /**
     * 月次健康情報集計バッチのSTEP<br>
     * monthlyHealthInfoSummaryBatchStep
     * 
     * @param jobRepository
     *     {@linkplain JobRepository}
     * @param transactionManager
     *     {@linkplain PlatformTransactionManager}
     * @return 月次健康情報集計のSTEP
     */
    @Bean(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_STEP_NAME)
    Step monthlyHealthInfoSummaryBatchStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(MONTHLY_HEALTH_INFO_SUMMARY_BATCH_STEP_NAME, jobRepository)
                .tasklet(monthlyHealthInfoSummaryBatch, transactionManager)
                .build();
    }
}
