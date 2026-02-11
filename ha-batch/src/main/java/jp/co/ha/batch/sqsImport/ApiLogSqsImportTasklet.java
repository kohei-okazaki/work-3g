package jp.co.ha.batch.sqsImport;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.component.ApiLogComponent;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.common.aws.AwsProperties;
import jp.co.ha.common.aws.AwsSqsComponent;
import jp.co.ha.common.aws.AwsSqsComponent.DequeueResult;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.CollectionUtil;

/**
 * API_LOG用AWS SQS取込-tasklet
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class ApiLogSqsImportTasklet implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(ApiLogSqsImportTasklet.class);
    /** AWS設定ファイル情報 */
    @Autowired
    private AwsProperties awsProps;
    /** AWS SQS */
    @Autowired
    private AwsSqsComponent sqs;
    /** API通信ログComponent */
    @Autowired
    private ApiLogComponent component;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        // キュー名
        String queueName = awsProps.apiLogQueueName();

        // キュー取得
        while (true) {
            List<DequeueResult<ApiLogQueuePayload>> queueList = sqs
                    .pollQueueBatchWithHandle(queueName, ApiLogQueuePayload.class, 3, 60,
                            10);

            if (CollectionUtil.isEmpty(queueList)) {
                // キューが登録されていない場合
                LOG.debug("queue not exist.");
                break;
            }

            for (DequeueResult<ApiLogQueuePayload> queueResult : queueList) {
                // API_LOG登録
                component.create(queueResult.getPayload());
                // キュー削除
                sqs.ackOne(queueName, queueResult.getReceiptHandle());
            }
        }

        return RepeatStatus.FINISHED;
    }

}
