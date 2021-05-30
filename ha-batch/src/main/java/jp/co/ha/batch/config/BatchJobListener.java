package jp.co.ha.batch.config;

import java.util.Map.Entry;
import java.util.StringJoiner;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.StringUtil;

/**
 * Batch処理のリスナークラス
 *
 * @version 1.0.0
 */
@Component
public class BatchJobListener extends JobExecutionListenerSupport {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BatchJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {

        StringJoiner sj = new StringJoiner(StringUtil.COMMA);
        sj.add("start JOB");
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
        super.afterJob(jobExecution);
        LOG.debug("end JOB. exit_code=" + jobExecution.getExitStatus());
    }

}
