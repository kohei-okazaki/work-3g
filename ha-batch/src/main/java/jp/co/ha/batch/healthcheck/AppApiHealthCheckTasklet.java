package jp.co.ha.batch.healthcheck;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.HealthCheckApi;
import jp.co.ha.business.api.healthinfoapp.request.HealthCheckApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.api.healthinfoapp.response.HealthCheckApiResponse;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.common.web.api.ApiConnectInfo;

/**
 * 健康管理API.ヘルスチェックAPI-Tasklet
 * 
 * @version 1.0.0
 */
@StepScope
@Component
public class AppApiHealthCheckTasklet extends BaseHealthCheckApiTasklet {

    /** ヘルスチェックAPI */
    @Autowired
    private HealthCheckApi healthCheckApi;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        ExecutionContext executionContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        String transactionId = getTransactionId(executionContext);

        executionContext.putString(KEY_TRANSACTION_ID, transactionId);

        HealthCheckApiRequest request = new HealthCheckApiRequest();
        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(
                        () -> healthInfoProperties.healthInfoApiUrl() + "healthcheck");

        HealthCheckApiResponse response = healthCheckApi.callApi(request, connectInfo);

        boolean failed = response == null || response.getResultType() != ResultType.SUCCESS;
        executionContext.put(KEY_APP_API_FAILED, failed);
        executionContext.putString(KEY_RESPONSE_TYPE,
                failed ? ResultType.FAILURE.name() : ResultType.SUCCESS.name());
        executionContext.putString(KEY_API_NAME, "healthinfo api");
        executionContext.putString(KEY_TITLE_TEXT, "健康管理API ヘルスチェックAPI結果");

        ApiLogQueuePayload payload = apiLogComponent.getPayload4AppApi(healthCheckApi,
                connectInfo, request, response, transactionId);
        apiLogComponent.registQueue(payload);

        return RepeatStatus.FINISHED;
    }

}
