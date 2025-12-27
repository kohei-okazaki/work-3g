package jp.co.ha.batch.analysis;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.base.BatchProperties;
import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.io.file.csv.model.DailyUserCsvModel;

/**
 * 日次ユーザ情報データ分析連携バッチ-Writer<br>
 * <ul>
 * <li>日次ユーザ情報CSV作成</li>
 * <li>S3アップロード</li>
 * <li>Slack通知</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyUserWriter extends BaseDailyAnalysisWriter<DailyUserCsvModel> {

    /** CSV項目名配列 */
    private static final String[] COLUMN_NAME_ARRAY = new String[] { "seqUserId",
            "genderType", "birthDate", "deleteFlag", "passwordExpire", "remarks",
            "goalWeight", "regDate", "updateDate", "headerFlag", "footerFlag", "maskFlag",
            "enclosureCharFlag" };

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
    public DailyUserWriter(BatchProperties batchProps, AwsS3Component s3,
            SlackApiComponent slack, String targetDate) {
        super(batchProps, s3, slack, targetDate);
    }

    @Override
    protected String getTempDirPath(BatchProperties batchProps) {
        return batchProps.getDailyUserAnalysis().getTempDirPath();
    }

    @Override
    protected String getFileName(BatchProperties batchProps) {
        return batchProps.getDailyUserAnalysis().getFileName();
    }

    @Override
    protected String[] getColumnArray() {
        return COLUMN_NAME_ARRAY;
    }

}
