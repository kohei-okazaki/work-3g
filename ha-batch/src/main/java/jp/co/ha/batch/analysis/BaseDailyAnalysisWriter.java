package jp.co.ha.batch.analysis;

import static jp.co.ha.business.api.slack.SlackApiComponent.ContentType.*;
import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;
import static jp.co.ha.common.util.FileUtil.FileExtension.*;
import static jp.co.ha.common.util.StringUtil.*;

import java.io.File;
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
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.common.aws.AwsS3Component;
import jp.co.ha.common.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * 日次共通データ分析連携バッチ-Writer<br>
 * 
 * @version 1.0.0
 * @param <T>
 *     CSVモデル型
 */
@Component
@StepScope
public abstract class BaseDailyAnalysisWriter<T extends BaseCsvModel>
        extends FlatFileItemWriter<T> implements StepExecutionListener {

    /** LOG */
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

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
     *     処理対象日
     */
    public BaseDailyAnalysisWriter(BatchProperties batchProps, AwsS3Component s3,
            SlackApiComponent slack, @Value("#{jobParameters[d]}") String targetDate) {

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
            String baseDir = getTempDirPath(batchProps);
            FileUtil.mkdir(baseDir);

            // 「テーブル名.csv」 を取得
            targetPath = Paths.get(baseDir, getFileName(batchProps) + CSV);
            LOG.debug("targetPath=%s".formatted(targetPath));

            setResource(new FileSystemResource(targetPath));
        } catch (BaseException e) {
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
                LOG.error("stepExecutionが失敗しました。");
                return;
            }

            // 正常終了時

            // 圧縮形式のファイルパスを取得
            Path gzPath = Paths.get(targetPath.toString() + GZ);
            FileUtil.compressGZip(targetPath, gzPath);

            File gzFile = gzPath.toFile();

            // analysis/YYYYMMDD/${table_name}.csv.gz
            String s3key = getS3Key(gzFile);

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
     * 一時ディレクトリパスを返す
     * 
     * @param batchProps
     *     バッチプロパティファイル
     * @return 一時ディレクトリパス
     */
    protected abstract String getTempDirPath(BatchProperties batchProps);

    /**
     * ファイル名を返す
     * 
     * @param batchProps
     *     バッチプロパティファイル
     * @return ファイル名
     */
    protected abstract String getFileName(BatchProperties batchProps);

    /**
     * 配列形式のカラム名を返す
     * 
     * @return 配列形式のカラム名
     */
    protected abstract String[] getColumnArray();

    /**
     * 初期化処理
     */
    private void init() {

        // 対象年月(YYYYMMDD)
        targetDate = StringUtil.isEmpty(targetDate)
                ? DateTimeUtil.toString(DateTimeUtil.getSysDate(), YYYYMMDD_NOSEP)
                : targetDate;

        setName(this.getClass().getSimpleName());
        setAppendAllowed(false);
        setShouldDeleteIfExists(true);
        setSaveState(true);

        BeanWrapperFieldExtractor<T> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(getColumnArray());

        DelimitedLineAggregator<T> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(COMMA);
        aggregator.setFieldExtractor(extractor);

        setLineAggregator(aggregator);

        setHeaderCallback(writer -> writer.write(String.join(COMMA, getColumnArray())));
    }

    /**
     * 指定されたgzファイルからS3キーを返す<br>
     * S3キー: analysis/YYYYMMDD/${table_name}.csv.gz
     * 
     * @param gzFile
     *     gzファイル
     * @return S3キー
     */
    private String getS3Key(File gzFile) {

        return new StringJoiner(THRASH)
                .add(AwsS3Key.DAILY_ANALYSIS.getValue())
                .add(targetDate)
                .add(gzFile.getName())
                .toString();
    }
}
