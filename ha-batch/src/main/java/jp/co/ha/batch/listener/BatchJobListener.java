package jp.co.ha.batch.listener;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.util.StringJoiner;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameter;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.exception.BaseAppError;
import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.StringUtil;

/**
 * Jobのリスナークラス
 *
 * @version 1.0.0
 */
@Component
public class BatchJobListener implements JobExecutionListener {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BatchJobListener.class);
    /** 開始メッセージフォーマット */
    private static final String MESSAGE_FORMAT = "id=%s, name=%s, status=%s";
    /** Slack Component */
    @Autowired
    private SlackApiComponent slack;
    /** MessageSource */
    @Autowired
    private MessageSource messageSource;

    @Override
    public void beforeJob(JobExecution jobExecution) {

        StringJoiner sj = new StringJoiner(StringUtil.COMMA);
        sj.add("start JOB. " + MESSAGE_FORMAT.formatted(jobExecution.getId(),
                jobExecution.getJobInstance().getJobName(), jobExecution.getStatus()));
        for (JobParameter<?> parameter : jobExecution.getJobParameters().parameters()) {
            sj.add("{key=" + parameter.name() + ", val=" + parameter.value() + "}");
        }
        LOG.debug(sj.toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        String message = MESSAGE_FORMAT.formatted(jobExecution.getId(),
                jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
        if (BatchStatus.FAILED == jobExecution.getStatus()) {
            for (Throwable t : jobExecution.getAllFailureExceptions()) {
                BaseAppError error = getAppError(t);

                StringBuilder errorMessage = new StringBuilder()
                        .append(messageSource.getMessage(
                                error.getErrorCode().getOuterErrorCode(), null,
                                LocaleContextHolder.getLocale()))
                        .append("(")
                        .append(error.getErrorCode().getOuterErrorCode())
                        .append(")");
                message += (", error_message=" + errorMessage.toString());
                slack.send(ContentType.BATCH, message);

                slack.sendError(ContentType.BATCH, t);
            }
        }

        // Slackに通知
        slack.send(ContentType.BATCH, message);
        // ログ出力
        LOG.debug("end JOB. message=%s".formatted(message));
    }

    /**
     * アプリケーション例外クラスを返す
     * 
     * @param t
     *     例外
     * @return アプリケーション例外
     */
    private BaseAppError getAppError(Throwable t) {
        BaseAppError error = null;
        if (t instanceof BaseAppError) {
            error = (BaseAppError) t;
        } else {
            BaseErrorCode errorCode = UNEXPECTED_ERROR;
            String detail = messageSource.getMessage(
                    errorCode.getOuterErrorCode(), null, LocaleContextHolder.getLocale());
            error = new SystemException(errorCode, detail);
        }

        return error;
    }

}
