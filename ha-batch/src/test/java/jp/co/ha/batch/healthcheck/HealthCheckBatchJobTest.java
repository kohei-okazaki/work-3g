package jp.co.ha.batch.healthcheck;

import static jp.co.ha.batch.base.BatchConfigConst.*;
import static jp.co.ha.batch.healthcheck.BaseHealthCheckApiTasklet.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Properties;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.support.TaskExecutorJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JdbcJobRepositoryFactoryBean;
import org.springframework.batch.core.step.Step;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;

import jp.co.ha.batch.listener.BatchJobListener;
import jp.co.ha.business.api.healthinfoapp.HealthCheckApi;
import jp.co.ha.business.api.healthinfoapp.request.HealthCheckApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.api.healthinfoapp.response.HealthCheckApiResponse;
import jp.co.ha.business.api.root.response.BaseRootApiResponse.Result;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.ApiLogComponent;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.aws.AwsSesComponent;
import jp.co.ha.common.aws.AwsSystemsManagerComponent;
import jp.co.ha.common.log.Logger.LogLevel;
import jp.co.ha.common.web.api.ApiConnectInfo;

/**
 * 両APIの通知完了後にジョブ全体の成否を確定し、結果をDBに保存することを確認する。
 */
class HealthCheckBatchJobTest {

    private final HealthCheckApi appApi = mock(HealthCheckApi.class);
    private final jp.co.ha.business.api.root.HealthCheckApi rootApi = mock(
            jp.co.ha.business.api.root.HealthCheckApi.class);
    private final ApiLogComponent apiLog = mock(ApiLogComponent.class);
    private final SlackApiComponent slack = mock(SlackApiComponent.class);
    private final AwsSesComponent ses = mock(AwsSesComponent.class);
    private final AwsSystemsManagerComponent ssm = mock(AwsSystemsManagerComponent.class);
    private final BatchJobListener listener = mock(BatchJobListener.class);
    private final JobParameters parameters = new JobParametersBuilder()
            .addString("run.id", "health-check-test")
            .toJobParameters();

    private JdbcTemplate jdbc;
    private TaskExecutorJobOperator operator;
    private Job job;

    @BeforeEach
    void setUp() throws Exception {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1", "sa", "");
        jdbc = new JdbcTemplate(dataSource);
        new ResourceDatabasePopulator(new ClassPathResource(
                "org/springframework/batch/core/schema-h2.sql")).execute(dataSource);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(
                dataSource);
        JdbcJobRepositoryFactoryBean repositoryFactory = new JdbcJobRepositoryFactoryBean();
        repositoryFactory.setDataSource(dataSource);
        repositoryFactory.setTransactionManager(transactionManager);
        repositoryFactory.afterPropertiesSet();
        JobRepository repository = repositoryFactory.getObject();

        when(apiLog.transactionId()).thenReturn("health-check-transaction");
        when(ssm.getValue(AwsSystemsManagerComponent.KEY_SYSTEM_MAILADDRESS))
                .thenReturn("system@example.test");

        HealthInfoProperties properties = new HealthInfoProperties(
                "", "", "", "https://app.example.test/", "https://root.example.test/",
                "", false);
        AppApiHealthCheckTasklet appTasklet = new AppApiHealthCheckTasklet();
        ReflectionTestUtils.setField(appTasklet, "healthCheckApi", appApi);
        ReflectionTestUtils.setField(appTasklet, "apiLogComponent", apiLog);
        ReflectionTestUtils.setField(appTasklet, "healthInfoProperties", properties);
        RootApiHealthCheckTasklet rootTasklet = new RootApiHealthCheckTasklet();
        ReflectionTestUtils.setField(rootTasklet, "healthCheckApi", rootApi);
        ReflectionTestUtils.setField(rootTasklet, "apiLogComponent", apiLog);
        ReflectionTestUtils.setField(rootTasklet, "healthInfoProperties", properties);
        HealthCheckNotifyTasklet notifyTasklet = new HealthCheckNotifyTasklet();
        ReflectionTestUtils.setField(notifyTasklet, "slack", slack);
        ReflectionTestUtils.setField(notifyTasklet, "ses", ses);
        ReflectionTestUtils.setField(notifyTasklet, "ssm", ssm);

        // 基底ConfigのComponentScanを起動せず、実際のStepとFlowを組み立てる。
        HealthCheckConfig configuration = new HealthCheckConfig();
        ReflectionTestUtils.setField(configuration, "appApiHealthCheckTasklet", appTasklet);
        ReflectionTestUtils.setField(configuration, "rootApiHealthCheckTasklet", rootTasklet);
        ReflectionTestUtils.setField(configuration, "notifyTasklet", notifyTasklet);
        ReflectionTestUtils.setField(configuration, "resultTasklet",
                new HealthCheckResultTasklet());
        Step appStep = configuration.appApiHealthCheckStep(repository, transactionManager);
        Step rootStep = configuration.rootApihealthCheckStep(repository, transactionManager);
        Step notifyStep = configuration.healthCheckNotifyStep(repository, transactionManager);
        Step resultStep = configuration.healthCheckResultStep(repository, transactionManager);
        job = configuration.healthCheckBatchJob(repository, appStep, rootStep, notifyStep,
                resultStep, listener);

        MapJobRegistry registry = new MapJobRegistry();
        registry.register(job);
        operator = new TaskExecutorJobOperator();
        operator.setJobRepository(repository);
        operator.setJobRegistry(registry);
        operator.setTaskExecutor(new SyncTaskExecutor());
        operator.afterPropertiesSet();
    }

    @AfterEach
    void tearDown() {
        if (jdbc != null) {
            jdbc.execute("SHUTDOWN");
        }
    }

    @ParameterizedTest(name = "appFailure={0}, rootFailure={1}: {2}")
    @CsvSource({
            "false, false, COMPLETED",
            "true, false, FAILED",
            "false, true, FAILED",
            "true, true, FAILED"
    })
    void recordsOverallStatusAfterCheckingAndNotifyingBothApis(
            boolean appFailure, boolean rootFailure, BatchStatus expectedStatus) throws Exception {
        stubResults(appFailure, rootFailure);

        JobExecution execution = operator.start(job, parameters);

        assertPersistedStatus(execution, expectedStatus);
        assertEquals(appFailure, execution.getExecutionContext().get(KEY_APP_API_FAILED));
        assertEquals(rootFailure, execution.getExecutionContext().get(KEY_ROOT_API_FAILED));
        assertStepStatus(execution, HEALTH_CHECK_APP_API_STEP_NAME, BatchStatus.COMPLETED);
        assertStepStatus(execution, HEALTH_CHECK_ROOT_API_STEP_NAME, BatchStatus.COMPLETED);
        assertStepStatus(execution, HEALTH_CHECK_RESULT_STEP_NAME, expectedStatus);
        assertEquals(2, jdbc.queryForObject("""
                SELECT COUNT(*) FROM BATCH_STEP_EXECUTION
                WHERE JOB_EXECUTION_ID = ? AND STEP_NAME = ? AND STATUS = 'COMPLETED'
                """, Integer.class, execution.getId(), HEALTH_CHECK_NOTIFY_STEP_NAME));

        var ordered = inOrder(appApi, rootApi, slack);
        ordered.verify(appApi).callApi(any(HealthCheckApiRequest.class), any(ApiConnectInfo.class));
        ordered.verify(slack).send(ContentType.BATCH,
                appFailure ? "healthinfo api server down" : "healthinfo api server up");
        ordered.verify(rootApi).callApi(
                any(jp.co.ha.business.api.root.request.HealthCheckApiRequest.class),
                any(ApiConnectInfo.class));
        ordered.verify(slack).send(ContentType.BATCH,
                rootFailure ? "root api server down" : "root api server up");
        verify(apiLog, times(2)).registQueue(isNull());
        verify(ses, times((appFailure ? 1 : 0) + (rootFailure ? 1 : 0)))
                .sendMail(eq("system@example.test"), anyString(),
                        eq(AwsSesComponent.MailTemplateKey.HEALTHINFO_CHECK_TEMPLATE));
        verify(listener).afterJob(execution);
        if (expectedStatus == BatchStatus.FAILED) {
            BusinessException failure = execution.getAllFailureExceptions().stream()
                    .filter(BusinessException.class::isInstance)
                    .map(BusinessException.class::cast)
                    .findFirst().orElseThrow();
            assertSame(BusinessErrorCode.HEALTH_CHECK_ERROR, failure.getErrorCode());
            assertEquals(LogLevel.ERROR, failure.getErrorCode().getLogLevel());
            assertEquals(appFailure, failure.getDetail().contains("healthinfo api"));
            assertEquals(rootFailure, failure.getDetail().contains("root api"));

            // Listenerが専用エラーコードに対応する通知メッセージを解決できることを確認する。
            Properties messages = new Properties();
            try (var input = new ClassPathResource("businessErrorCode.properties")
                    .getInputStream()) {
                messages.load(input);
            }
            String message = messages.getProperty(failure.getErrorCode().getOuterErrorCode());
            assertNotNull(message);
            assertFalse(message.isBlank());
        }
    }

    @Test
    void rechecksAndNotifiesBothApisWhenRestartingFailedJobWithSameParameters() throws Exception {
        stubResults(true, true);
        JobExecution firstExecution = operator.start(job, parameters);
        assertPersistedStatus(firstExecution, BatchStatus.FAILED);

        stubResults(false, false);
        JobExecution restartedExecution = operator.restart(firstExecution);

        assertPersistedStatus(restartedExecution, BatchStatus.COMPLETED);
        assertNotEquals(firstExecution.getId(), restartedExecution.getId());
        assertEquals(firstExecution.getJobInstance(), restartedExecution.getJobInstance());
        assertEquals(false, restartedExecution.getExecutionContext().get(KEY_APP_API_FAILED));
        assertEquals(false, restartedExecution.getExecutionContext().get(KEY_ROOT_API_FAILED));
        verify(appApi, times(2)).callApi(any(HealthCheckApiRequest.class),
                any(ApiConnectInfo.class));
        verify(rootApi, times(2)).callApi(
                any(jp.co.ha.business.api.root.request.HealthCheckApiRequest.class),
                any(ApiConnectInfo.class));
        verify(slack).send(ContentType.BATCH, "healthinfo api server down");
        verify(slack).send(ContentType.BATCH, "healthinfo api server up");
        verify(slack).send(ContentType.BATCH, "root api server down");
        verify(slack).send(ContentType.BATCH, "root api server up");
    }

    @Test
    void doesNotMaskUnexpectedCheckStepFailureAfterSuccessfulApiResponses() throws Exception {
        stubResults(false, false);
        doThrow(new IllegalStateException("通信ログの登録失敗"))
                .doNothing().when(apiLog).registQueue(isNull());

        JobExecution execution = operator.start(job, parameters);

        assertPersistedStatus(execution, BatchStatus.FAILED);
        // Batch 6.0.6のStepState/JobFlowExecutorは失敗後に次のStepへ進むと
        // 直前のStepをABANDONEDにする。最終判定はこの異常状態も検出する。
        assertStepStatus(execution, HEALTH_CHECK_APP_API_STEP_NAME, BatchStatus.ABANDONED);
        assertStepStatus(execution, HEALTH_CHECK_ROOT_API_STEP_NAME, BatchStatus.COMPLETED);
        assertStepStatus(execution, HEALTH_CHECK_RESULT_STEP_NAME, BatchStatus.FAILED);
        verify(rootApi).callApi(
                any(jp.co.ha.business.api.root.request.HealthCheckApiRequest.class),
                any(ApiConnectInfo.class));
        verify(slack).send(ContentType.BATCH, "healthinfo api server up");
        verify(slack).send(ContentType.BATCH, "root api server up");
    }

    private void stubResults(boolean appFailure, boolean rootFailure) {
        HealthCheckApiResponse appResponse = new HealthCheckApiResponse();
        appResponse.setResultType(appFailure ? ResultType.FAILURE : ResultType.SUCCESS);
        when(appApi.callApi(any(HealthCheckApiRequest.class), any(ApiConnectInfo.class)))
                .thenReturn(appResponse);
        var rootResponse = new jp.co.ha.business.api.root.response.HealthCheckApiResponse();
        rootResponse.setResult(rootFailure ? Result.FAILURE : Result.SUCCESS);
        when(rootApi.callApi(
                any(jp.co.ha.business.api.root.request.HealthCheckApiRequest.class),
                any(ApiConnectInfo.class))).thenReturn(rootResponse);
    }

    private void assertPersistedStatus(JobExecution execution, BatchStatus expectedStatus) {
        assertEquals(expectedStatus, execution.getStatus());
        assertEquals(expectedStatus.name(), execution.getExitStatus().getExitCode());
        assertEquals(expectedStatus.name(), jdbc.queryForObject(
                "SELECT STATUS FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID = ?",
                String.class, execution.getId()));
    }

    private void assertStepStatus(JobExecution execution, String stepName,
            BatchStatus expectedStatus) {
        assertEquals(expectedStatus.name(), jdbc.queryForObject("""
                SELECT STATUS FROM BATCH_STEP_EXECUTION
                WHERE JOB_EXECUTION_ID = ? AND STEP_NAME = ?
                """, String.class, execution.getId(), stepName));
    }
}
