package jp.co.ha.batch.analysis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
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

import jp.co.ha.batch.base.BatchProperties;
import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.io.file.csv.model.DailyHealthInfoCsvModel;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;

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
public class DailyHealthInfoWriter extends FlatFileItemWriter<DailyHealthInfoCsvModel>
        implements StepExecutionListener {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(DailyHealthInfoWriter.class);
    /** CSV項目名配列 */
    private static final String[] COLUMN_NAME_ARRAY = new String[] { "seqHealthInfoId",
            "seqUserId", "height", "weight", "bmi", "standardWeight",
            "healthInfoRegDate" };
    /** バッチプロパティファイル */
    private BatchProperties batchProps;
    /** AWS-S3 Component */
    private AwsS3Component s3;
    /** Slack Component */
    private SlackApiComponent slack;
    /** 処理対象年月 */
    private String targetDate;
    /** 出力対象パス */
    private Path targetPath;
    /** StepExecution */
    private StepExecution stepExecution;

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
    public DailyHealthInfoWriter(
            BatchProperties batchProps,
            AwsS3Component s3,
            SlackApiComponent slack,
            @Value("#{jobParameters[d]}") String targetDate) {

        this.batchProps = batchProps;
        this.s3 = s3;
        this.slack = slack;
        this.targetDate = targetDate;

        init();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

        try {
            String baseDir = batchProps.getDailyHealthInfoAnalysis()
                    .getTempDirPath();
            Files.createDirectories(Paths.get(baseDir));

            targetPath = Paths.get(baseDir, "healthinfo" + FileExtension.CSV);

            setResource(new FileSystemResource(targetPath));
        } catch (IOException e) {
            throw new ItemStreamException(e);
        }
        super.open(executionContext);
    }

    @Override
    public void close() {
        // CSV作成
        super.close();

        try {
            if (Objects.isNull(stepExecution)
                    || stepExecution.getStatus().isUnsuccessful()) {
                // 異常終了時
                return;
            } else if (stepExecution.getWriteCount() == 0) {
                // 対象データ0件ならアップロードしない
                // TODO 空ファイルを作成して配置
                Files.deleteIfExists(targetPath);
                return;
            }

            // 正常終了時
            Path gzPath = Paths.get(targetPath.toString() + ".gz");
            FileUtil.compressGZip(targetPath, gzPath);

            // 検索対象年月(YYYYMMDD)
            String date = StringUtil.isEmpty(targetDate)
                    ? DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                            DateFormatType.YYYYMMDD_NOSEP)
                    : targetDate;

            File gzFile = gzPath.toFile();
            StringJoiner s3Path = new StringJoiner(StringUtil.THRASH)
                    .add(AwsS3Key.DAILY_ANALYSIS.getValue())
                    .add(date)
                    .add(gzFile.getName());
            // analysis/YYYYMMDD/healthinfo.csv.gz
            String s3key = s3Path.toString();

            s3.putFile(s3key, gzFile);

            // Slack通知
            slack.sendFile(ContentType.BATCH, gzFile, "S3ファイルアップロード完了. key=" + s3key);

            // ファイル送信が正常終了した場合、ローカルファイルを削除
            Files.deleteIfExists(targetPath);

        } catch (Exception e) {
            LOG.error("gzip圧縮/S3アップロードに失敗しました", e);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getStatus().isUnsuccessful()) {
            return ExitStatus.FAILED;
        }
        return ExitStatus.COMPLETED;
    }

    /**
     * 初期化処理
     */
    private void init() {

        setName("dailyHealthInfoWriter");
        setAppendAllowed(false);
        setShouldDeleteIfExists(true);
        setSaveState(true);

        BeanWrapperFieldExtractor<DailyHealthInfoCsvModel> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(COLUMN_NAME_ARRAY);

        DelimitedLineAggregator<DailyHealthInfoCsvModel> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(StringUtil.COMMA);
        aggregator.setFieldExtractor(extractor);

        setLineAggregator(aggregator);

        setHeaderCallback(
                writer -> writer.write(String.join(StringUtil.COMMA, COLUMN_NAME_ARRAY)));
    }

}
