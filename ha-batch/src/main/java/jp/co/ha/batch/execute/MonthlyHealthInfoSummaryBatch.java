package jp.co.ha.batch.execute;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.HealthInfoSearchServiceImpl;
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
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.BatchBeanLoader;
import jp.co.ha.common.system.SystemMemory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.DateUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 月次健康情報集計バッチ<br>
 * 引数1 処理対象月(YYYYMM)
 *
 * @version 1.0.0
 */
public class MonthlyHealthInfoSummaryBatch extends BaseBatch {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(MonthlyHealthInfoSummaryBatch.class);
    /** S3キーの接頭辞 */
    private static final String PREFIX_S3_KEY = "monthly/healthinfo/";
    /** 健康情報検索サービス */
    private HealthInfoSearchService searchService = BatchBeanLoader
            .getBean(HealthInfoSearchServiceImpl.class);
    /** 健康情報設定ファイル */
    private HealthInfoProperties prop = BatchBeanLoader
            .getBean(HealthInfoProperties.class);
    /** S3のComponent */
    private AwsS3Component s3 = BatchBeanLoader.getBean(AwsS3Component.class);

    @Override
    public BatchResult execute(CommandLine cmd) throws BaseException {

        LOG.info("月次健康情報集計Batch START メモリ使用量"
                + SystemMemory.getInstance().getMemoryUsage());

        // 処理対象年月
        String targetDate = cmd.getOptionValue("m",
                DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMM_NOSEP));

        // 健康情報リスト
        List<HealthInfo> healthInfoList = getHealthInfoList(targetDate);
        // 月次健康情報集計CSV Modelリスト
        List<MonthlyHealthInfoSummaryModel> modelList = toModelList(healthInfoList);
        // 月次健康情報集計CSV
        File csv = writeCsv(targetDate, modelList);
        // S3ファイルをアップロード
        s3.putFile(PREFIX_S3_KEY + csv.getName(), csv);

        LOG.info("月次健康情報集計Batch END メモリ使用量"
                + SystemMemory.getInstance().getMemoryUsage());

        return BatchResult.SUCCESS;
    }

    @Override
    public Options getOptions() {
        Options options = new Options();
        options.addOption("m", true, "処理対象年月");
        return options;
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
        Date from = DateUtil.toDate(year + "/" + month + "/01 00:00:00",
                DateFormatType.YYYYMMDDHHMMSS);

        int lastDay = DateUtil.getLastDay(year, month);
        Date to = DateUtil.toDate(year + "/" + month + "/" + lastDay + " 23:59:59",
                DateFormatType.YYYYMMDDHHMMSS);

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("HEALTH_INFO_REG_DATE", SortType.DESC)
                .orderBy("USER_ID", SortType.ASC)
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
            model.setHealthInfoRegDate(DateUtil.toString(e.getHealthInfoRegDate(),
                    DateFormatType.YYYYMMDDHHMMSS));
            model.setRegDate(DateUtil.toString(e.getRegDate(),
                    DateFormatType.YYYYMMDDHHMMSS));
            model.setUpdateDate(DateUtil.toString(e.getUpdateDate(),
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
