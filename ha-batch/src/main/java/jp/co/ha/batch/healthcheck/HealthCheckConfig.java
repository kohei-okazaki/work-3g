package jp.co.ha.batch.healthcheck;

import static jp.co.ha.batch.base.BatchConfigConst.*;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
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
 * ヘルスチェックバッチのConfig
 * 
 * @version 1.0.0
 */
@Configuration
public class HealthCheckConfig extends BatchConfig {

    /** 健康管理API.ヘルスチェックAPI-Tasklet */
    @Autowired
    private AppApiHealthCheckTasklet appApiHealthCheckTasklet;
    /** 管理者用API.ヘルスチェックAPI-Tasklet */
    @Autowired
    private RootApiHealthCheckTasklet rootApiHealthCheckTasklet;
    /** 共通通知tasklet */
    @Autowired
    private HealthCheckNotifyTasklet notifyTasklet;

    /**
     * ヘルスチェックバッチのJOB
     *
     * @param jobRepository
     *     jobRepository
     * @param appApihealthCheckStep
     *     健康管理アプリヘルスチェックStep
     * @param rootApihealthCheckStep
     *     管理者用APIヘルスチェックStep
     * @param healthCheckNotifyStep
     *     通知用Step
     * @param listener
     *     BatchJobListener
     * @return ヘルスチェックバッチJOB
     */
    @Bean(HEALTH_CHECK_BACTH_JOB_NAME)
    Job healthCheckBatchJob(JobRepository jobRepository,
            @Qualifier(HEALTH_CHECK_APP_API_STEP_NAME) Step appApihealthCheckStep,
            @Qualifier(HEALTH_CHECK_ROOT_API_STEP_NAME) Step rootApihealthCheckStep,
            @Qualifier(HEALTH_CHECK_NOTIFY_STEP_NAME) Step healthCheckNotifyStep,
            BatchJobListener listener) {

        Flow appApiHealthCheckFlow = new FlowBuilder<Flow>(HEALTH_CHECK_APP_API_FLOW_NAME)
                .start(appApihealthCheckStep)
                .on("*")
                .to(healthCheckNotifyStep)
                .end();

        Flow rootApiHealthCheckFlow = new FlowBuilder<Flow>(
                HEALTH_CHECK_ROOT_API_FLOW_NAME)
                        .start(rootApihealthCheckStep)
                        .on("*")
                        .to(healthCheckNotifyStep)
                        .end();

        return new JobBuilder(HEALTH_CHECK_BACTH_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(appApiHealthCheckFlow)
                .next(rootApiHealthCheckFlow)
                .end()
                .build();
    }

    /**
     * 健康管理APIヘルスチェックStep
     * 
     * @param jobRepository
     *     jobRepository
     * @param transactionManager
     *     transactionManager
     * @return 健康管理APIヘルスチェックStep
     */
    @Bean
    Step appApiHealthCheckStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(HEALTH_CHECK_APP_API_STEP_NAME, jobRepository)
                .tasklet(appApiHealthCheckTasklet, transactionManager)
                .build();
    }

    /**
     * 管理者用APIヘルスチェックStep
     * 
     * @param jobRepository
     *     jobRepository
     * @param transactionManager
     *     transactionManager
     * @return 管理者用APIヘルスチェックStep
     */
    @Bean
    Step rootApihealthCheckStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(HEALTH_CHECK_ROOT_API_STEP_NAME, jobRepository)
                .tasklet(rootApiHealthCheckTasklet, transactionManager)
                .build();
    }

    /**
     * 通知用Step
     * 
     * @param jobRepository
     *     jobRepository
     * @param transactionManager
     *     transactionManager
     * @return 通知用Step
     */
    @Bean
    Step healthCheckNotifyStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(HEALTH_CHECK_NOTIFY_STEP_NAME, jobRepository)
                .tasklet(notifyTasklet, transactionManager)
                .build();
    }
}
