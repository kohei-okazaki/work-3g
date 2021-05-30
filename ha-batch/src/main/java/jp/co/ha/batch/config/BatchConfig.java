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
import jp.co.ha.batch.ｍonthlyHealthInfoSummary.MonthlyHealthInfoSummaryBatch;
import jp.co.ha.batch.ｍonthlyHealthInfoSummary.MonthlyHealthInfoSummaryValidator;

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
    /** {@linkplain StepBuilderFactory} */
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
     * heathCheckApiBatchJob
     *
     * @param heathCheckApiBatchStep
     *     ヘルスチェックAPIバッチのSTEP
     * @return ヘルスチェックAPIバッチJOB
     */
    @Bean
    public Job heathCheckApiBatchJob(Step heathCheckApiBatchStep) {
        return jobBuilderFactory.get("heathCheckApiBatchJob")
                .incrementer(new RunIdIncrementer())
                .listener(batchJobListener)
                // 実行するStepを指定
                .flow(heathCheckApiBatchStep)
                .end()
                .build();
    }

    /**
     * ヘルスチェックAPIバッチのSTEP<br>
     * heathCheckApiBatchStep
     *
     * @return ヘルスチェックAPIバッチのSTEP
     */
    @Bean
    public Step heathCheckApiBatchStep() {
        // Step名を指定
        return stepBuilderFactory.get("heathCheckApiBatchStep")
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
     * ｍonthlyHealthInfoSummaryBatchJob
     *
     * @param ｍonthlyHealthInfoSummaryBatchStep
     *     月次健康情報集計バッチのSTEP
     * @return 月次健康情報集計バッチJOB
     */
    @Bean
    public Job ｍonthlyHealthInfoSummaryBatchJob(Step ｍonthlyHealthInfoSummaryBatchStep) {
        return jobBuilderFactory.get("ｍonthlyHealthInfoSummaryBatchJob")
                .incrementer(new RunIdIncrementer())
                .validator(new MonthlyHealthInfoSummaryValidator())
                .listener(batchJobListener)
                // 実行するStepを指定
                .flow(ｍonthlyHealthInfoSummaryBatchStep)
                .end()
                .build();
    }

    /**
     * 月次健康情報集計バッチのSTEP<br>
     * ｍonthlyHealthInfoSummaryBatchStep
     *
     * @return 月次健康情報集計のSTEP
     */
    @Bean
    public Step ｍonthlyHealthInfoSummaryBatchStep() {
        // Step名を指定
        return stepBuilderFactory.get("ｍonthlyHealthInfoSummaryBatchStep")
                // 実行するTaskletを指定
                .tasklet(monthlyHealthInfoSummaryBatch)
                .build();
    }

}
