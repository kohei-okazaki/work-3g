package jp.co.ha.batch.monthlyHealthInfoSummary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.io.file.csv.model.MonthlyHealthInfoSummaryModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;

/**
 * 月次健康情報集計処理-Writer<br>
 * <ul>
 * <li>月次健康情報集計CSV作成</li>
 * <li>S3アップロード</li>
 * <li>Slack通知</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class MonthlyHealthInfoSummaryWriter
        extends FlatFileItemWriter<MonthlyHealthInfoSummaryModel>
        implements StepExecutionListener {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(MonthlyHealthInfoSummaryWriter.class);
    /** CSV項目名配列 */
    private static final String[] COLUMN_NAME_ARRAY = new String[] {
            "seqUserId", "height", "weight", "bmi", "standardWeight",
            "healthInfoRegDate", "seqBmiRangeMtId", "updateDate",
            "regDate"
    };
    /** 健康情報設定ファイル */
    private HealthInfoProperties prop;
    /** AWS-S3 Component */
    private AwsS3Component s3;
    /** Slack Component */
    private SlackApiComponent slack;
    /** 処理対象年月 */
    private String targetDate;
    /** 出力対象パス */
    private Path targetPath;

    /**
     * コンストラクタ
     * 
     * @param prop
     *     健康情報設定ファイル
     * @param s3
     *     AWS-S3 Component
     * @param slack
     *     Slack Component
     * @param targetDate
     *     処理対象年月
     */
    public MonthlyHealthInfoSummaryWriter(
            HealthInfoProperties prop,
            AwsS3Component s3,
            SlackApiComponent slack,
            @Value("#{jobParameters[m]}") String targetDate) {

        this.prop = prop;
        this.s3 = s3;
        this.slack = slack;
        this.targetDate = targetDate;

        init();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

        try {
            String baseDir = prop.getMonthlySummaryBatchFilePath();
            Files.createDirectories(Paths.get(baseDir));

            String baseName = targetDate + FileExtension.CSV;
            targetPath = Paths.get(baseDir, baseName);

            setResource(new FileSystemResource(targetPath));
        } catch (IOException e) {
            throw new ItemStreamException(e);
        }
        super.open(executionContext);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        try {
            if (stepExecution.getStatus().isUnsuccessful()) {
                // 異常終了時
                return ExitStatus.FAILED;
            }

            // 正常終了時
            File csv = targetPath.toFile();
            StringJoiner s3Path = new StringJoiner(StringUtil.THRASH)
                    .add(AwsS3Key.MONTHLY_HEALTHINFO_SUMMARY.getValue())
                    .add("year=" + csv.getName().substring(0, 4))
                    .add(csv.getName());
            String s3key = s3Path.toString();

            // TODO 対象データがない場合、[ExitStatus.COMPLETED]する

            s3.putFile(s3key, csv);

            // Slack通知
            slack.sendFile(ContentType.BATCH, csv, "S3ファイルアップロード完了. key=" + s3key);

        } catch (BaseException e) {
            LOG.error("S3アップロードに失敗しました", e);
            return ExitStatus.FAILED.addExitDescription(e.getMessage());
        }
        return ExitStatus.COMPLETED;
    }

    /**
     * 初期化処理
     */
    private void init() {

        setName("monthlyHealthInfoSummaryWriter");
        setAppendAllowed(false);
        setShouldDeleteIfExists(true);
        setSaveState(true);

        BeanWrapperFieldExtractor<MonthlyHealthInfoSummaryModel> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(COLUMN_NAME_ARRAY);

        DelimitedLineAggregator<MonthlyHealthInfoSummaryModel> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(StringUtil.COMMA);
        aggregator.setFieldExtractor(extractor);

        setLineAggregator(aggregator);
    }

}
