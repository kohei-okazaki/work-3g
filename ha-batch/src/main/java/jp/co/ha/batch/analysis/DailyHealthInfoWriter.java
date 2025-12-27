package jp.co.ha.batch.analysis;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.base.BatchProperties;
import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.io.file.csv.model.DailyHealthInfoCsvModel;
import jp.co.ha.common.util.FileUtil.FileExtension;

/**
 * 日次健康情報データ分析連携バッチ-Writer<br>
 * <ul>
 * <li>日次健康情報CSV作成</li>
 * <li>S3アップロード</li>
 * <li>Slack通知</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyHealthInfoWriter
        extends BaseDailyAnalysisWriter<DailyHealthInfoCsvModel> {

    /** CSV項目名配列 */
    private static final String[] COLUMN_NAME_ARRAY = new String[] { "seqHealthInfoId",
            "seqUserId", "height", "weight", "bmi", "standardWeight",
            "healthInfoRegDate" };

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
     *     処理対象年月日
     * @param batchProperties
     *     バッチプロパティファイル
     */
    public DailyHealthInfoWriter(BatchProperties batchProps, AwsS3Component s3,
            SlackApiComponent slack, @Value("#{jobParameters[d]}") String targetDate) {
        super(batchProps, s3, slack, targetDate);
    }

    @Override
    public String getTempDirPath(BatchProperties batchProps) {
        return batchProps.getDailyHealthInfoAnalysis()
                .getTempDirPath();
    }

    @Override
    public String getFileName(BatchProperties batchProps) {
        return batchProps.getDailyHealthInfoAnalysis().getFileName() + FileExtension.CSV;
    }

    @Override
    public String[] getColumnArray() {
        return COLUMN_NAME_ARRAY;
    }

}
