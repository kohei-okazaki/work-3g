package jp.co.ha.batch.execute;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.csv.model.MonthlyHealthInfoSummaryModel;
import jp.co.ha.business.io.file.csv.writer.MonthlyHealthInfoSummaryCsvWriter;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.CsvConfig.CsvConfigBuilder;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 月次健康情報集計バッチ<br>
 * 引数1 処理対象月(YYYYMM)
 *
 * @version 1.0.0
 */
@Component("monthlyHealthInfoSummaryBatch")
public class MonthlyHealthInfoSummaryBatch extends BaseBatch {

    /** S3キーの接頭辞 */
    private static final String PREFIX_S3_KEY = "monthly/healthinfo/";

    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService searchService;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;
    /** S3のComponent */
    @Autowired
    private AwsS3Component s3;

    @Override
    public BatchResult execute(CommandLine cmd) throws BaseException {

        // 処理対象年月
        String targetDate = cmd.getOptionValue("m",
                DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMM_NOSEP));

        validate(targetDate);

        // 健康情報リスト
        List<HealthInfo> healthInfoList = getHealthInfoList(targetDate);
        // 月次健康情報集計CSV Modelリスト
        List<MonthlyHealthInfoSummaryModel> modelList = toModelList(healthInfoList);
        // 月次健康情報集計CSV
        File csv = writeCsv(targetDate, modelList);
        // S3ファイルをアップロード
        s3.putFile(PREFIX_S3_KEY + csv.getName(), csv);

        return BatchResult.SUCCESS;
    }

    @Override
    public Options getOptions() {
        Options options = new Options();
        options.addOption("m", true, "処理対象年月");
        return options;
    }

    /**
     * 処理対象年月の妥当性チェックを行う
     *
     * @param date
     *     対象日付
     * @throws BusinessException
     *     日付形式でない場合、エラー
     */
    private void validate(String date) throws BusinessException {

        if (StringUtil.isEmpty(date)) {
            // 未指定の場合、エラー
            throw new BusinessException(CommonErrorCode.VALIDATE_ERROR,
                    "-m is required -m=" + date);
        } else if (!DateTimeUtil.isDate(date, DateFormatType.YYYYMM_NOSEP)) {
            throw new BusinessException(CommonErrorCode.VALIDATE_ERROR,
                    "-m is not date format -m=" + date);
        }
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

        int lastDay = DateTimeUtil.getLastDayOfMonth(from);
        LocalDateTime to = LocalDateTime.of(year, month, lastDay, 23, 59, 59);

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("HEALTH_INFO_REG_DATE", SortType.DESC)
                .orderBy("SEQ_USER_ID", SortType.ASC)
                .build();

        return searchService
                .findByBetweenHealthInfoRegDate(from, to, selectOption);
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
            BeanUtil.copy(e, model, Arrays.asList("serialVersionUID"));
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

        String fileName = targetDate + FileExtension.CSV.getValue();
        File file = new File(prop.getMonthlySummaryBatchFilePath()
                + FileSeparator.SYSTEM.getValue() + fileName);
        CsvConfig conf = new CsvConfigBuilder(fileName,
                prop.getMonthlySummaryBatchFilePath()).build();

        try (PrintWriter pw = new PrintWriter(file);
                MonthlyHealthInfoSummaryCsvWriter writer = new MonthlyHealthInfoSummaryCsvWriter(
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
