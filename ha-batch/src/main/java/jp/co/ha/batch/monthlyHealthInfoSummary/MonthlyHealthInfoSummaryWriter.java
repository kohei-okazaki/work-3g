package jp.co.ha.batch.monthlyHealthInfoSummary;

import static jp.co.ha.business.api.slack.SlackApiComponent.ContentType.*;
import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;
import static jp.co.ha.common.util.FileUtil.FileExtension.*;
import static jp.co.ha.common.util.StringUtil.*;

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

import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.io.file.csv.model.MonthlyHealthInfoSummaryModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.aws.AwsS3Component;
import jp.co.ha.common.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.FileUtil;

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
    /** StepExecution */
    private StepExecution stepExecution;

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
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

        try {
            String baseDir = prop.getMonthlySummaryBatchFilePath();
            Files.createDirectories(Paths.get(baseDir));

            targetPath = Paths.get(baseDir, targetDate + CSV);

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
                Files.deleteIfExists(targetPath);
                return;
            }

            // 正常終了時
            Path gzPath = Paths.get(targetPath.toString() + GZ);
            FileUtil.compressGZip(targetPath, gzPath);

            File gzFile = gzPath.toFile();
            StringJoiner s3Path = new StringJoiner(THRASH)
                    .add(AwsS3Key.MONTHLY_HEALTHINFO_SUMMARY.getValue())
                    .add("year=" + gzFile.getName().substring(0, 4))
                    .add(gzFile.getName());
            String s3key = s3Path.toString();

            s3.putFile(s3key, gzFile);

            // Slack通知
            slack.sendFile(BATCH, gzFile, "S3ファイルアップロード完了. key=" + s3key);

            // ファイル送信が正常終了した場合、ローカルファイルを削除
            Files.deleteIfExists(targetPath);
            Files.deleteIfExists(gzPath);

        } catch (Exception e) {
            LOG.error("gzip圧縮/S3アップロードに失敗しました", e);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return stepExecution.getStatus().isUnsuccessful() ? ExitStatus.FAILED
                : ExitStatus.COMPLETED;
    }

    /**
     * 初期化処理
     */
    private void init() {

        targetDate = isEmpty(targetDate)
                ? DateTimeUtil.toString(DateTimeUtil.getSysDate(), YYYYMM_NOSEP)
                : targetDate;

        setName("monthlyHealthInfoSummaryWriter");
        setAppendAllowed(false);
        setShouldDeleteIfExists(true);
        setSaveState(true);

        BeanWrapperFieldExtractor<MonthlyHealthInfoSummaryModel> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(COLUMN_NAME_ARRAY);

        DelimitedLineAggregator<MonthlyHealthInfoSummaryModel> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(COMMA);
        aggregator.setFieldExtractor(extractor);

        setLineAggregator(aggregator);

        setHeaderCallback(
                writer -> writer.write(String.join(COMMA, COLUMN_NAME_ARRAY)));
    }

}
