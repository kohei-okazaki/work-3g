package jp.co.ha.batch.sqsImport;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.aws.AwsProperties;
import jp.co.ha.business.api.aws.AwsSqsComponent;
import jp.co.ha.business.api.aws.AwsSqsComponent.DequeueResult;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.dto.ApiCommunicationDataQueuePayload;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.CollectionUtil;

/**
 * API_COMMUNICATION_DATA用AWS SQS取込-tasklet
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class ApiCommunicationDataSqsImportTasklet implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(ApiCommunicationDataSqsImportTasklet.class);
    /** AWS設定ファイル情報 */
    @Autowired
    private AwsProperties awsProps;
    /** AWS SQS */
    @Autowired
    private AwsSqsComponent sqs;
    /** API通信情報Component */
    @Autowired
    protected ApiCommunicationDataComponent apiCommunicationDataComponent;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        // キュー名
        String queueName = awsProps.getApiCommunicationDataQueueName();

        // キュー取得
        List<DequeueResult<ApiCommunicationDataQueuePayload>> queueList = sqs
                .pollQueueBatchWithHandle(queueName, ApiCommunicationDataQueuePayload.class,
                        3, 60, 10);

        if (CollectionUtil.isEmpty(queueList)) {
            // キューが登録されていない場合
            LOG.debug("queue not exist.");
            return RepeatStatus.FINISHED;
        }

        // キュー情報をDBへ登録
        for (DequeueResult<ApiCommunicationDataQueuePayload> queueResult : queueList) {
            // API_COMMUNICATION_DATA登録
            apiCommunicationDataComponent.create(queueResult.getPayload());
            // キュー削除
            sqs.ackOne(queueName, queueResult.getReceiptHandle());
        }

        return RepeatStatus.FINISHED;
    }

}
