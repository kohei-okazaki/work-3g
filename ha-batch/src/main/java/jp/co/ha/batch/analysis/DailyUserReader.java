package jp.co.ha.batch.analysis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.base.BatchProperties;
import jp.co.ha.db.entity.composite.CompositeUser;
import jp.co.ha.db.mapper.composite.CompositeUserMapper;

/**
 * 日次ユーザ情報データ分析連携バッチ-Reader
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyUserReader extends BaseDailyAnalysisReader<CompositeUser> {

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
    public DailyUserReader(SqlSessionFactory sqlSessionFactory,
            @Value("#{jobParameters[d]}") String targetDate,
            BatchProperties batchProperties) {
        super(sqlSessionFactory, targetDate, batchProperties);
    }

    @Override
    public String getQueryId() {
        return CompositeUserMapper.class.getName() + ".selectUserRegDate";
    }

    @Override
    public int getPageSize(BatchProperties batchProperties) {
        return batchProperties.getDailyUserAnalysis().getExecPerpageCount();
    }
}
