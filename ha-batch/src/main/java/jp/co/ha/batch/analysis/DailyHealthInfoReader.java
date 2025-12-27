package jp.co.ha.batch.analysis;

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
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.mapper.composite.CommonHealthInfoMapper;

/**
 * 日次健康情報データ分析連携バッチ-Reader
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyHealthInfoReader extends MyBatisPagingItemReader<HealthInfo> {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(DailyHealthInfoReader.class);

    /**
     * コンストラクタ
     * 
     * @param sqlSessionFactory
     *     SqlSessionFactory
     * @param targetDate
     *     処理対象年月日
     * @param batchProperties
     *     バッチプロパティファイル
     */
    public DailyHealthInfoReader(SqlSessionFactory sqlSessionFactory,
            @Value("#{jobParameters[d]}") String targetDate,
            BatchProperties batchProperties) {

        // 検索対象年月(YYYYMMDD)
        String date = StringUtil.isEmpty(targetDate)
                ? DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDD_NOSEP)
                : targetDate;
        LOG.debug("targetDate=" + date);

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        LocalDateTime from = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(year, month, day, 23, 59, 59);
        LOG.debug("from=" + from + ", to=" + to);

        setSqlSessionFactory(sqlSessionFactory);

        setQueryId(
                CommonHealthInfoMapper.class.getName() + ".selectByHealthInfoRegDate");

        Map<String, Object> params = Map.of(
                "from", from, "to", to);

        setParameterValues(params);
        // 1ページの取得件数
        setPageSize(batchProperties.getDailyHealthInfoAnalysis().getExecPerpageCount());
        // // 再実行用に状態保存（デフォルト true）
        setSaveState(true);
    }
}
