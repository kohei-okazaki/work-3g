package jp.co.ha.batch.analysis;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.base.BatchProperties;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.io.file.csv.model.DailyBatchLogCsvModel;
import jp.co.ha.common.aws.AwsS3Component;

/**
 * 日次バッチ実行ログデータ分析連携バッチ-Writer<br>
 * <ul>
 * <li>日次バッチ実行ログCSV作成</li>
 * <li>S3アップロード</li>
 * <li>Slack通知</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyBatchLogWriter extends BaseDailyAnalysisWriter<DailyBatchLogCsvModel> {

    /** CSV項目名配列 */
    private static final String[] COLUMN_NAME_ARRAY = new String[] { "jobInstanceId",
            "jobName", "startDate", "endDate" };

    /**
     * コンストラクタ
     * 
     * @param batchProps
     *     バッチプロパティファイル
     * @param s3
     *     AWS-S3 Component
     * @param slack
     *     Slack Component
     * @param targetDate
     *     処理対象日
     */
    public DailyBatchLogWriter(BatchProperties batchProps, AwsS3Component s3,
            SlackApiComponent slack, @Value("#{jobParameters[d]}") String targetDate) {
        super(batchProps, s3, slack, targetDate);
    }

    @Override
    protected String getTempDirPath(BatchProperties batchProps) {
        return batchProps.getDailyBatchLogAnalysis().getTempDirPath();
    }

    @Override
    protected String getFileName(BatchProperties batchProps) {
        return batchProps.getDailyBatchLogAnalysis().getFileName();
    }

    @Override
    protected String[] getColumnArray() {
        return COLUMN_NAME_ARRAY;
    }

}
