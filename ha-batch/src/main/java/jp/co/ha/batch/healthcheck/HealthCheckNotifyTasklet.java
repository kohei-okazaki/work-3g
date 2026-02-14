package jp.co.ha.batch.healthcheck;

import static jp.co.ha.batch.healthcheck.BaseHealthCheckApiTasklet.*;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.aws.AwsSesComponent;
import jp.co.ha.common.aws.AwsSystemsManagerComponent;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * ヘルスチェックバッチ.共通通知用tasklet
 * 
 * @version 1.0.0
 */
@StepScope
@Component
public class HealthCheckNotifyTasklet implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(HealthCheckNotifyTasklet.class);

    /** AWS Systems Manager Component */
    @Autowired
    private AwsSystemsManagerComponent ssm;
    /** AWS S3 Component */
    @Autowired
    private AwsSesComponent ses;
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slack;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        ExecutionContext executionContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        switch (executionContext.getString(KEY_RESPONSE_TYPE)) {
        case "SUCCESS":
            // 成功の場合
            LOG.debug("%s server up".formatted(executionContext.getString(KEY_API_NAME)));
            slack.send(ContentType.BATCH,
                    "%s server up".formatted(executionContext.getString(KEY_API_NAME)));
            break;
        default:
            // 成功以外の場合
            LOG.error("%s  server down"
                    .formatted(executionContext.getString(KEY_API_NAME)));
            ses.sendMail(ssm.getValue(AwsSystemsManagerComponent.KEY_SYSTEM_MAILADDRESS),
                    executionContext.getString(KEY_TITLE_TEXT), TEMPLATE_ID);
            slack.send(ContentType.BATCH,
                    "%s server down".formatted(executionContext.getString(KEY_API_NAME)));
            break;
        }

        return RepeatStatus.FINISHED;
    }

}
