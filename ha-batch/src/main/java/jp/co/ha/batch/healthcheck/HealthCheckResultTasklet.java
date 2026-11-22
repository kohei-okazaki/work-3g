package jp.co.ha.batch.healthcheck;

import static jp.co.ha.batch.healthcheck.BaseHealthCheckApiTasklet.*;
import static jp.co.ha.business.exception.BusinessErrorCode.*;

import java.util.StringJoiner;

import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * 両APIのチェック・通知後に、ヘルスチェックバッチ全体の結果を判定する。
 *
 * @version 1.0.0
 */
@Component
public class HealthCheckResultTasklet implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckResultTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws BusinessException {

        JobExecution jobExecution = chunkContext.getStepContext()
                .getStepExecution().getJobExecution();
        ExecutionContext executionContext = jobExecution.getExecutionContext();
        StringJoiner failures = new StringJoiner(", ");

        // 結果が未保存の場合も、成功を確認できていないため異常とする。
        if (!Boolean.FALSE.equals(executionContext.get(KEY_APP_API_FAILED))) {
            failures.add("healthinfo api");
        }
        if (!Boolean.FALSE.equals(executionContext.get(KEY_ROOT_API_FAILED))) {
            failures.add("root api");
        }

        // 通知へ進むフローで、チェックStepの例外が吸収されないようにする。
        jobExecution.getStepExecutions().stream()
                .filter(step -> step.getStatus().isUnsuccessful())
                .forEach(step -> failures
                        .add(step.getStepName() + " (" + step.getStatus() + ")"));

        if (failures.length() > 0) {
            String detail = "ヘルスチェックに失敗しました: " + failures;
            LOG.error(detail);
            throw new BusinessException(HEALTH_CHECK_ERROR, detail);
        }

        return RepeatStatus.FINISHED;
    }
}
