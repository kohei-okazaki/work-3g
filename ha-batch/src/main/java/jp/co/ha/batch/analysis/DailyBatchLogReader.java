package jp.co.ha.batch.analysis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.base.BatchProperties;
import jp.co.ha.db.entity.custom.CustomJobData;
import jp.co.ha.db.mapper.custom.CustomJobMapper;

/**
 * 日次バッチ実行ログデータ分析連携バッチ-Reader
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyBatchLogReader extends BaseDailyAnalysisMySQLReader<CustomJobData> {

    /**
     * コンストラクタ
     * 
     * @param sqlSessionFactory
     *     SqlSessionFactory
     * @param targetDate
     *     処理対象日
     * @param batchProperties
     *     バッチプロパティファイル
     */
    public DailyBatchLogReader(SqlSessionFactory sqlSessionFactory,
            @Value("#{jobParameters[d]}") String targetDate,
            BatchProperties batchProperties) {
        super(sqlSessionFactory, targetDate, batchProperties);
    }

    @Override
    public String getQueryId() {
        return CustomJobMapper.class.getName() + ".selectByCreateTime";
    }

    @Override
    public int getPageSize(BatchProperties batchProperties) {
        return batchProperties.getDailyBatchLogAnalysis().getExecPerpageCount();
    }
}
