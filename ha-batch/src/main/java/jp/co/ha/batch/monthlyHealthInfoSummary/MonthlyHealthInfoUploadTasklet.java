package jp.co.ha.batch.monthlyHealthInfoSummary;

import java.io.File;
import java.util.Objects;
import java.util.StringJoiner;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.util.StringUtil;

/**
 * 月次健康情報アップロード-tasklet
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class MonthlyHealthInfoUploadTasklet implements Tasklet {

    /** AWS-S3 Component */
    @Autowired
    private AwsS3Component s3;
    /** Slack Component */
    @Autowired
    private SlackApiComponent slack;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        ExecutionContext executionContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        // 月次健康情報集計CSV
        File csv = executionContext.get("csv", File.class);
        if (Objects.isNull(csv)) {
            // 月次健康情報集計CSVがない場合
            return RepeatStatus.FINISHED;
        }
        // S3ファイルをアップロード
        StringJoiner sj = new StringJoiner(StringUtil.THRASH)
                .add(AwsS3Key.MONTHLY_HEALTHINFO_SUMMARY.getValue())
                .add("year=" + csv.getName().substring(0, 4))
                .add(csv.getName());
        String s3key = sj.toString();
        s3.putFile(s3key, csv);

        // Slack通知
        slack.sendFile(ContentType.BATCH, csv, "S3ファイルアップロード完了. key=" + s3key);
        slack.send(ContentType.BATCH,
                "monthly_health_info_summary_batch success.");

        return RepeatStatus.FINISHED;
    }

}
