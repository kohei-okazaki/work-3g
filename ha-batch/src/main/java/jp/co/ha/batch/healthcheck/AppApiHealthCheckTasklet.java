package jp.co.ha.batch.healthcheck;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.HealthCheckApi;
import jp.co.ha.business.api.healthinfoapp.request.HealthCheckApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.HealthCheckApiResponse;
import jp.co.ha.business.dto.ApiCommunicationDataQueuePayload;
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

        HealthCheckApiRequest req = new HealthCheckApiRequest();
        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(
                        () -> healthInfoProperties.getHealthInfoApiUrl() + "healthcheck");

        ApiCommunicationDataQueuePayload payload = apiCommunicationDataComponent
                .getPayload(transactionId, healthCheckApi.getApiName(),
                        healthCheckApi.getHttpMethod(),
                        healthCheckApi.getUri(connectInfo, req),
                        req);

        HealthCheckApiResponse response = healthCheckApi.callApi(req, connectInfo);

        apiCommunicationDataComponent
                .fillResponseParam(payload, connectInfo, response);

        apiCommunicationDataComponent.inQueue(payload);

        executionContext.put(KEY_RESPONSE_TYPE, response.getResultType().toString());
        executionContext.put(KEY_API_NAME, "healthinfo api");
        executionContext.put(KEY_TITLE_TEXT, "健康管理API ヘルスチェックAPI結果");

        return RepeatStatus.FINISHED;
    }

}
