package jp.co.ha.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ha.batch.healthInfoFileRegist.HealthInfoFileRegistBatch;
import jp.co.ha.batch.healthcheck.HealthCheckApiBatch;
import jp.co.ha.batch.listener.BatchJobListener;
import jp.co.ha.batch.monthlyHealthInfoSummary.MonthlyHealthInfoSummaryBatch;
import jp.co.ha.batch.monthlyHealthInfoSummary.MonthlyHealthInfoSummaryValidator;

/**
 * Batch処理の定義クラス
 *
 * @version 1.0.0
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    /** {@linkplain JobBuilderFactory} */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    /** {@linkplain StepBuilderFactory} */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    /** {@linkplain BatchJobListener} */
    @Autowired
    private BatchJobListener batchJobListener;
    /** {@linkplain HealthCheckApiBatch} */
    @Autowired
    private HealthCheckApiBatch healthCheckApiBatch;
    /** {@linkplain HealthInfoFileRegistBatch} */
    @Autowired
    private HealthInfoFileRegistBatch healthInfoFileRegistBatch;
    /** {@linkplain HealthInfoFileRegistBatch} */
    @Autowired
    private MonthlyHealthInfoSummaryBatch monthlyHealthInfoSummaryBatch;

    /**
     * ヘルスチェックAPIバッチのJOB<br>
     * healthCheckApiBatchJob
     *
     * @param healthCheckApiBatchStep
     *     ヘルスチェックAPIバッチのSTEP
     * @return ヘルスチェックAPIバッチJOB
     */
    @Bean
    public Job healthCheckApiBatchJob(Step healthCheckApiBatchStep) {
        return jobBuilderFactory.get("healthCheckApiBatchJob")
                .incrementer(new RunIdIncrementer())
                .listener(batchJobListener)
                // 実行するStepを指定
                .flow(healthCheckApiBatchStep)
                .end()
                .build();
    }

    /**
     * ヘルスチェックAPIバッチのSTEP<br>
     * healthCheckApiBatchStep
     *
     * @return ヘルスチェックAPIバッチのSTEP
     */
    @Bean
    public Step healthCheckApiBatchStep() {
        // Step名を指定
        return stepBuilderFactory.get("healthCheckApiBatchStep")
                // 実行するTaskletを指定
                .tasklet(healthCheckApiBatch)
                .build();
    }

    /**
     * 健康情報一括登録バッチのJOB<br>
     * healthInfoFileRegistBatchJob
     *
     * @param healthInfoFileRegistBatchStep
     *     健康情報一括登録バッチのSTEP
     * @return 健康情報一括登録バッチJOB
     */
    @Bean
    public Job healthInfoFileRegistBatchJob(Step healthInfoFileRegistBatchStep) {
        return jobBuilderFactory.get("healthInfoFileRegistBatchJob")
                .incrementer(new RunIdIncrementer())
                .listener(batchJobListener)
                // 実行するStepを指定
                .flow(healthInfoFileRegistBatchStep)
                .end()
                .build();
    }

    /**
     * 健康情報一括登録バッチのSTEP<br>
     * healthInfoFileRegistBatchStep
     *
     * @return 健康情報一括登録バッチのSTEP
     */
    @Bean
    public Step healthInfoFileRegistBatchStep() {
        // Step名を指定
        return stepBuilderFactory.get("healthInfoFileRegistBatchStep")
                // 実行するTaskletを指定
                .tasklet(healthInfoFileRegistBatch)
                .build();
    }

    /**
     * 月次健康情報集計バッチのJOB<br>
     * monthlyHealthInfoSummaryBatchJob
     *
     * @param monthlyHealthInfoSummaryBatchStep
     *     月次健康情報集計バッチのSTEP
     * @return 月次健康情報集計バッチJOB
     */
    @Bean
    public Job monthlyHealthInfoSummaryBatchJob(Step monthlyHealthInfoSummaryBatchStep) {
        return jobBuilderFactory.get("monthlyHealthInfoSummaryBatchJob")
                .incrementer(new RunIdIncrementer())
                .validator(new MonthlyHealthInfoSummaryValidator())
                .listener(batchJobListener)
                // 実行するStepを指定
                .flow(monthlyHealthInfoSummaryBatchStep)
                .end()
                .build();
    }

    /**
     * 月次健康情報集計バッチのSTEP<br>
     * monthlyHealthInfoSummaryBatchStep
     *
     * @return 月次健康情報集計のSTEP
     */
    @Bean
    public Step monthlyHealthInfoSummaryBatchStep() {
        // Step名を指定
        return stepBuilderFactory.get("monthlyHealthInfoSummaryBatchStep")
                // 実行するTaskletを指定
                .tasklet(monthlyHealthInfoSummaryBatch)
                .build();
    }

}
