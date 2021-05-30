package jp.co.ha.batch.ｍonthlyHealthInfoSummary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Key;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.csv.model.MonthlyHealthInfoSummaryModel;
import jp.co.ha.business.io.file.csv.writer.MonthlyHealthInfoSummaryCsvWriter;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.CsvConfig.CsvConfigBuilder;
import jp.co.ha.common.io.file.csv.writer.CsvWriter;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 月次健康情報集計バッチ<br>
 * <ul>
 * <li>引数1=処理対象月(YYYYMM)</li>
 * </ul>
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class MonthlyHealthInfoSummaryBatch implements Tasklet {

    /** 処理対象日付 */
    @Value("#{jobParameters[m]}")
    private String targetDate;
    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService searchService;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;
    /** S3のComponent */
    @Autowired
    private AwsS3Component s3;
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slackApiComponent;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        targetDate = StringUtil.isEmpty(targetDate)
                ? DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMM_NOSEP)
                : targetDate;

        // 健康情報リスト
        List<HealthInfo> healthInfoList = getHealthInfoList(targetDate);
        // 月次健康情報集計CSV Modelリスト
        List<MonthlyHealthInfoSummaryModel> modelList = toModelList(healthInfoList);
        // 月次健康情報集計CSV
        File csv = writeCsv(targetDate, modelList);
        // S3ファイルをアップロード
        s3.putFile(AwsS3Key.MONTHLY_HEALTHINFO_SUMMARY.getValue() + csv.getName(), csv);

        // Slackのbatch_${env}チャンネルにメッセージを投稿
        slackApiComponent.send(ContentType.BATCH, "S3ファイルアップロード完了. key="
                + AwsS3Key.MONTHLY_HEALTHINFO_SUMMARY.getValue() + csv.getName());

        return RepeatStatus.FINISHED;
    }

    /**
     * 処理対象年月の健康情報リストを返す
     *
     * @param targetDate
     *     処理対象年月
     * @return 健康情報リスト
     */
    private List<HealthInfo> getHealthInfoList(String targetDate) {

        int year = Integer.parseInt(targetDate.substring(0, 4));
        int month = Integer.parseInt(targetDate.substring(4));
        LocalDateTime from = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(year, month,
                DateTimeUtil.getLastDayOfMonth(from), 23, 59, 59);

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("HEALTH_INFO_REG_DATE", SortType.DESC)
                .orderBy("SEQ_USER_ID", SortType.ASC)
                .build();

        return searchService.findBySeqUserIdBetweenHealthInfoRegDate(null, from, to,
                selectOption);
    }

    /**
     * 月次健康情報集計CSV Modelリストに変換する
     *
     * @param healthInfoList
     *     健康情報リスト
     * @return 月次健康情報集計CSV Model
     */
    private List<MonthlyHealthInfoSummaryModel> toModelList(
            List<HealthInfo> healthInfoList) {

        return healthInfoList.stream().map(e -> {
            MonthlyHealthInfoSummaryModel model = new MonthlyHealthInfoSummaryModel();
            BeanUtil.copy(e, model);
            model.setHealthInfoRegDate(DateTimeUtil.toString(e.getHealthInfoRegDate(),
                    DateFormatType.YYYYMMDDHHMMSS));
            model.setRegDate(DateTimeUtil.toString(e.getRegDate(),
                    DateFormatType.YYYYMMDDHHMMSS));
            model.setUpdateDate(DateTimeUtil.toString(e.getUpdateDate(),
                    DateFormatType.YYYYMMDDHHMMSS));
            return model;
        }).collect(Collectors.toList());
    }

    /**
     * 月次健康情報集計CSVを書込
     *
     * @param targetDate
     *     処理対象年月
     * @param modelList
     *     月次健康情報集計CSV Modelリスト
     * @return CSVファイル
     * @throws BaseException
     *     ファイルが存在しない場合
     */
    private File writeCsv(String targetDate,
            List<MonthlyHealthInfoSummaryModel> modelList) throws BaseException {

        String fileName = targetDate + FileExtension.CSV;
        File file = Paths.get(prop.getMonthlySummaryBatchFilePath(), fileName).toFile();
        CsvConfig conf = new CsvConfigBuilder(fileName,
                prop.getMonthlySummaryBatchFilePath()).build();

        try (PrintWriter pw = new PrintWriter(file);
                CsvWriter<MonthlyHealthInfoSummaryModel> writer = new MonthlyHealthInfoSummaryCsvWriter(
                        conf, pw)) {
            // CSVに書込
            writer.execute(modelList);
            writer.flush();
        } catch (FileNotFoundException e) {
            throw new BusinessException(e);
        }

        return file;
    }
}
