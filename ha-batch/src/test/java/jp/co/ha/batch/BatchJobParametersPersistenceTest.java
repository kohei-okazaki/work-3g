package jp.co.ha.batch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.batch.autoconfigure.BatchAutoConfiguration;
import org.springframework.boot.batch.autoconfigure.BatchJobLauncherAutoConfiguration;
import org.springframework.boot.batch.autoconfigure.JobLauncherApplicationRunner;
import org.springframework.boot.batch.jdbc.autoconfigure.BatchJdbcAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import jp.co.ha.batch.listener.BatchJobListener;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;

/**
 * シェルから渡す実行パラメータと通知IDが、アプリケーション終了後もDBに残ることを確認する。
 */
class BatchJobParametersPersistenceTest {

    @Test
    void shellRunIdIsPersistedAcrossApplicationRestartsAndMatchesSlackExecutionId() {
        String url = "jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1";
        DataSource dataSource = new DriverManagerDataSource(url, "sa", "");
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        new ResourceDatabasePopulator(new ClassPathResource(
                "org/springframework/batch/core/schema-h2.sql")).execute(dataSource);

        ApplicationContextRunner runner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(BatchAutoConfiguration.class,
                        BatchJdbcAutoConfiguration.class, BatchJobLauncherAutoConfiguration.class))
                .withInitializer(context -> context.getBeanFactory()
                        .registerSingleton("slack", mock(SlackApiComponent.class)))
                .withUserConfiguration(TestBatchConfiguration.class)
                .withPropertyValues("test.datasource.url=" + url,
                        "spring.batch.jdbc.initialize-schema=never",
                        "spring.batch.job.name=parameterPersistenceJob");
        try {
            runAndVerify(runner, jdbc, "20261122120000123456789-1001");
            long firstId = jdbc.queryForObject(
                    "SELECT MAX(JOB_EXECUTION_ID) FROM BATCH_JOB_EXECUTION", Long.class);

            runAndVerify(runner, jdbc, "20261122120100987654321-1002");

            assertEquals(2, jdbc.queryForObject(
                    "SELECT COUNT(*) FROM BATCH_JOB_EXECUTION", Integer.class));
            assertTrue(jdbc.queryForObject(
                    "SELECT MAX(JOB_EXECUTION_ID) FROM BATCH_JOB_EXECUTION", Long.class) > firstId);
        } finally {
            jdbc.execute("SHUTDOWN");
        }
    }

    private void runAndVerify(ApplicationContextRunner runner, JdbcTemplate jdbc, String runId) {
        runner.run(context -> {
            assertNull(context.getStartupFailure());
            context.getBean(JobLauncherApplicationRunner.class).run(new DefaultApplicationArguments(
                    "--spring.batch.job.name=parameterPersistenceJob",
                    "d=20261122", "run.id=" + runId));

            long executionId = jdbc.queryForObject("""
                    SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION_PARAMS
                    WHERE PARAMETER_NAME = 'run.id' AND PARAMETER_VALUE = ?
                    """, Long.class, runId);
            assertEquals("java.lang.String", jdbc.queryForObject("""
                    SELECT PARAMETER_TYPE FROM BATCH_JOB_EXECUTION_PARAMS
                    WHERE JOB_EXECUTION_ID = ? AND PARAMETER_NAME = 'run.id'
                    """, String.class, executionId));
            assertEquals("20261122", jdbc.queryForObject("""
                    SELECT PARAMETER_VALUE FROM BATCH_JOB_EXECUTION_PARAMS
                    WHERE JOB_EXECUTION_ID = ? AND PARAMETER_NAME = 'd'
                    """, String.class, executionId));
            assertEquals("COMPLETED", jdbc.queryForObject(
                    "SELECT STATUS FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID = ?",
                    String.class, executionId));
            verify(context.getBean(SlackApiComponent.class)).send(ContentType.BATCH,
                    "id=%s, name=parameterPersistenceJob, status=COMPLETED".formatted(executionId));
        });
    }

    @Configuration(proxyBeanMethods = false)
    static class TestBatchConfiguration {

        @Bean
        DataSource dataSource(Environment environment) {
            return new DriverManagerDataSource(
                    environment.getRequiredProperty("test.datasource.url"), "sa", "");
        }

        @Bean
        PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        BatchJobListener listener() {
            return new BatchJobListener();
        }

        @Bean
        Step parameterPersistenceStep(JobRepository repository,
                PlatformTransactionManager transactionManager) {
            return new StepBuilder("parameterPersistenceStep", repository)
                    .tasklet((contribution, context) -> RepeatStatus.FINISHED, transactionManager)
                    .build();
        }

        @Bean
        Job parameterPersistenceJob(JobRepository repository, Step parameterPersistenceStep,
                BatchJobListener listener) {
            return new JobBuilder("parameterPersistenceJob", repository)
                    .listener(listener)
                    .start(parameterPersistenceStep)
                    .build();
        }
    }
}
