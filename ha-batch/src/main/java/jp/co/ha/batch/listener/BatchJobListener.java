package jp.co.ha.batch.listener;

import java.util.Map.Entry;
import java.util.StringJoiner;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.StringUtil;

/**
 * Jobのリスナークラス
 *
 * @version 1.0.0
 */
@Component
public class BatchJobListener extends JobExecutionListenerSupport {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BatchJobListener.class);
    /** {@linkplain SlackApiComponent} */
    @Autowired
    private SlackApiComponent slackApiComponent;

    @Override
    public void beforeJob(JobExecution jobExecution) {

        StringJoiner sj = new StringJoiner(StringUtil.COMMA);
        sj.add("start JOB. name=" + jobExecution.getJobInstance().getJobName());
        for (Entry<String, JobParameter> entry : jobExecution.getJobParameters()
                .getParameters().entrySet()) {
            sj.add("{key=" + entry.getKey() + ", val=" + entry.getValue().toString()
                    + "}");
        }
        LOG.debug(sj.toString());
        super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        try {
            String message = "name=" + jobExecution.getJobInstance().getJobName()
                    + ", status=" + jobExecution.getStatus();
            if (BatchStatus.COMPLETED == jobExecution.getStatus()) {
                // 正常終了している場合
                slackApiComponent.send(ContentType.BATCH, message);
            }
            LOG.debug("end JOB. " + message);
        } catch (BaseException e) {
            throw new SystemRuntimeException(e);
        }
    }

}
