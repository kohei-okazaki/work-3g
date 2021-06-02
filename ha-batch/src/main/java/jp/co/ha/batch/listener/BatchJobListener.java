package jp.co.ha.batch.listener;

import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.StringJoiner;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.exception.BaseAppError;
import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
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
    /** {@linkplain MessageSource} */
    @Autowired
    protected MessageSource messageSource;

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
            if (BatchStatus.FAILED == jobExecution.getStatus()) {
                List<Throwable> exceptions = jobExecution.getAllFailureExceptions();
                for (Throwable t : exceptions) {
                    BaseAppError error = null;
                    if (t instanceof BaseAppError) {
                        error = (BaseAppError) t;
                    } else {
                        BaseErrorCode errorCode = CommonErrorCode.UNEXPECTED_ERROR;
                        String detail = messageSource.getMessage(
                                errorCode.getOuterErrorCode(), null,
                                Locale.getDefault());
                        error = new SystemException(errorCode, detail);
                    }

                    String errorMessage = new StringBuilder()
                            .append(messageSource.getMessage(
                                    error.getErrorCode().getOuterErrorCode(), null,
                                    Locale.getDefault()))
                            .append("(")
                            .append(error.getErrorCode().getOuterErrorCode())
                            .append(")")
                            .toString();
                    message += (", error_message=" + errorMessage);
                    slackApiComponent.send(ContentType.BATCH,
                            message + ", error_message=" + errorMessage);
                }
            }

            // Slackに通知
            slackApiComponent.send(ContentType.BATCH, message);
            // ログ出力
            LOG.debug("end JOB. " + message);

        } catch (BaseException e) {
            throw new SystemRuntimeException(e);
        }
    }

}
