package jp.co.ha.batch.analysis;

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

/**
 * 日次共通データ分析連携バッチ-Reader<br>
 * ※データ連携対象のデータ取得元がMySQLの場合、こちらのクラスを継承して実装すること。<br>
 * 
 * @version 1.0.0
 * @param <T>
 *     データ連携対象型
 */
@Component
@StepScope
public abstract class BaseDailyAnalysisMySQLReader<T> extends MyBatisPagingItemReader<T> {

    /** LOG */
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

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
    public BaseDailyAnalysisMySQLReader(SqlSessionFactory sqlSessionFactory,
            @Value("#{jobParameters[d]}") String targetDate,
            BatchProperties batchProperties) {

        if (useRange()) {
            // 検索対象年月(YYYYMMDD)
            String date = StringUtil.isEmpty(targetDate)
                    ? DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                            YYYYMMDD_NOSEP)
                    : targetDate;
            LOG.debug("targetDate=" + date);

            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6));
            LocalDateTime from = LocalDateTime.of(year, month, day, 0, 0, 0);
            LocalDateTime to = LocalDateTime.of(year, month, day, 23, 59, 59);
            LOG.debug("from=" + from + ", to=" + to);

            Map<String, Object> params = Map.of(
                    "from", from, "to", to);

            setParameterValues(params);
        }

        // SqlSessionFactory設定
        setSqlSessionFactory(sqlSessionFactory);
        // 検索SQL指定
        setQueryId(getQueryId());
        // 1ページの取得件数
        setPageSize(getPageSize(batchProperties));
        // // 再実行用に状態保存（デフォルト true）
        setSaveState(true);

    }

    /**
     * クエリIDを返す
     * 
     * @return クエリID
     */
    protected abstract String getQueryId();

    /**
     * 1ページあたりの件数を返す
     * 
     * @param batchProperties
     *     バッチプロパティファイル
     * @return 件数
     */
    protected abstract int getPageSize(BatchProperties batchProperties);

    /**
     * 区間指定の検索を行うかどうか
     * 
     * @return 区間指定の検索を行う場合、true。それ以外の場合、false
     */
    protected boolean useRange() {
        return true;
    }

}
