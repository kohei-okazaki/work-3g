package jp.co.ha.batch.healthcheck;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.root.HealthCheckApi;
import jp.co.ha.business.api.root.request.HealthCheckApiRequest;
import jp.co.ha.business.api.root.response.HealthCheckApiResponse;
import jp.co.ha.business.dto.ApiCommunicationDataQueuePayload;
import jp.co.ha.common.web.api.ApiConnectInfo;

/**
 * 管理者用API.ヘルスチェックAPI-Tasklet
 * 
 * @version 1.0.0
 */
@StepScope
@Component
public class RootApiHealthCheckTasklet extends BaseHealthCheckApiTasklet {

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
                        () -> healthInfoProperties.getRootApiUrl() + "healthcheck");

        HealthCheckApiResponse response = healthCheckApi.callApi(request, connectInfo);

        ApiCommunicationDataQueuePayload payload = apiCommunicationDataComponent
                .getPayload4RootApi(healthCheckApi, connectInfo, request, response,
                        transactionId);
        apiCommunicationDataComponent.registQueue(payload);

        executionContext.put(KEY_RESPONSE_TYPE, response.getResult().toString());
        executionContext.put(KEY_API_NAME, "root api");
        executionContext.put(KEY_TITLE_TEXT, "管理者用API.ヘルスチェックAPI結果");

        return RepeatStatus.FINISHED;
    }

}
