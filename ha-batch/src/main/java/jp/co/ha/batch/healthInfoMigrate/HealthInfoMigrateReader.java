package jp.co.ha.batch.healthInfoMigrate;

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
import jp.co.ha.db.mapper.custom.PagingHealthInfoMapper;

/**
 * 健康情報連携処理-Reader
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class HealthInfoMigrateReader extends MyBatisPagingItemReader<HealthInfo> {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(HealthInfoMigrateReader.class);

    /**
     * コンストラクタ
     * 
     * @param sqlSessionFactory
     *     SqlSessionFactory
     * @param targetDate
     *     処理対象日(YYYYMMDD)
     * @param batchProperties
     *     バッチプロパティファイル
     */
    public HealthInfoMigrateReader(SqlSessionFactory sqlSessionFactory,
            @Value("#{jobParameters[d]}") String targetDate,
            BatchProperties batchProperties) {

        String date = StringUtil.isEmpty(targetDate)
                ? DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDD_NOSEP)
                : targetDate;
        LOG.debug("targetDate=" + date);

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        LocalDateTime from = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(year, month, day, 23, 59, 59);
        LOG.debug("from=" + from + ", to=" + to);

        setSqlSessionFactory(sqlSessionFactory);

        setQueryId(
                PagingHealthInfoMapper.class.getName() + ".selectByHealthInfoRegDate");

        Map<String, Object> params = Map.of(
                "from", from, "to", to);

        setParameterValues(params);
        // 1ページの取得件数
        setPageSize(batchProperties.getHealthInfoMigrate().getExecPerpageCount());
        // // 再実行用に状態保存（デフォルト true）
        setSaveState(true);
    }
}
