package jp.co.ha.batch.monthlyHealthInfoSummary;

import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.base.BatchProperties;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.mapper.custom.PagingHealthInfoMapper;

/**
 * 月次健康情報集計処理-Reader
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class MonthlyHealthInfoSummaryReader extends MyBatisPagingItemReader<HealthInfo> {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(MonthlyHealthInfoSummaryReader.class);

    /**
     * コンストラクタ
     * 
     * @param sqlSessionFactory
     *     SqlSessionFactory
     * @param targetDate
     *     処理対象年月
     * @param batchProperties
     *     バッチプロパティファイル
     */
    public MonthlyHealthInfoSummaryReader(SqlSessionFactory sqlSessionFactory,
            @Value("#{jobParameters[m]}") String targetDate,
            BatchProperties batchProperties) {

        // 検索対象年月(YYYYMM)
        String date = StringUtil.isEmpty(targetDate)
                ? DateTimeUtil.toString(DateTimeUtil.getSysDate(), YYYYMM_NOSEP)
                : targetDate;
        LOG.debug("targetDate=" + date);

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4));
        LocalDateTime from = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(year, month,
                DateTimeUtil.getLastDayOfMonth(from), 23, 59, 59);
        LOG.debug("from=" + from + ", to=" + to);

        // SqlSessionFactory設定
        setSqlSessionFactory(sqlSessionFactory);
        // 検索SQL指定
        setQueryId(PagingHealthInfoMapper.class.getName() + ".selectByHealthInfoRegDate");
        // 検索パラメータ指定
        setParameterValues(Map.of("from", from, "to", to));
        // 1ページの取得件数
        setPageSize(batchProperties.getMonthlyHealthInfoSummary().getExecPerpageCount());
        // // 再実行用に状態保存（デフォルト true）
        setSaveState(true);
    }
}
