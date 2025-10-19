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

import jp.co.ha.business.api.aws.AwsSesComponent;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.SystemProperties;

/**
 * ヘルスチェックバッチ.共通通知用tasklet
 * 
 * @version 1.0.0
 */
@StepScope
@Component
public class NotifyTasklet implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(NotifyTasklet.class);

    /** AWS S3 Component */
    @Autowired
    private AwsSesComponent ses;
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slack;
    /** システム設定ファイル情報 */
    @Autowired
    private SystemProperties systemProperties;

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
            LOG.debug(executionContext.getString(KEY_API_NAME) + " server up");
            slack.send(ContentType.BATCH,
                    executionContext.getString(KEY_API_NAME) + " server up");
            break;
        default:
            // 成功以外の場合
            LOG.error(executionContext.getString(KEY_API_NAME) + " server down");
            ses.sendMail(systemProperties.getSystemMailAddress(),
                    executionContext.getString(KEY_TITLE_TEXT), TEMPLATE_ID);
            slack.send(ContentType.BATCH,
                    executionContext.getString(KEY_API_NAME) + " server down");
            break;
        }

        return RepeatStatus.FINISHED;
    }

}
