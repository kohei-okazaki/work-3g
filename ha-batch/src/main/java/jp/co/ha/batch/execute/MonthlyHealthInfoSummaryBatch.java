package jp.co.ha.batch.execute;

import java.util.Date;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.HealthInfoSearchServiceImpl;
import jp.co.ha.business.io.file.csv.model.MonthlyHealthInfoSummaryModel;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.BatchBeanLoader;
import jp.co.ha.common.system.SystemMemory;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.DateUtil.DateFormatType;
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
    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService searchService = BatchBeanLoader
            .getBean(HealthInfoSearchServiceImpl.class);

    @Override
    public BatchResult execute(CommandLine cmd) throws BaseException {

        LOG.info("月次健康情報集計Batch START メモリ使用量"
                + SystemMemory.getInstance().getMemoryUsage());

        // 処理対象年月
        String targetDate = cmd.getOptionValue("m",
                DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMM_NOSEP));

        // 健康情報リスト
        List<HealthInfo> healthInfoList = getHealthInfoList(targetDate);

        List<MonthlyHealthInfoSummaryModel> modelList = toModelList(healthInfoList);

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

    private List<MonthlyHealthInfoSummaryModel> toModelList(
            List<HealthInfo> healthInfoList) {
        // TODO 自動生成されたメソッド・スタブ
        return healthInfoList.stream().map(e -> {
            MonthlyHealthInfoSummaryModel model = new MonthlyHealthInfoSummaryModel();
        });
    }

}
