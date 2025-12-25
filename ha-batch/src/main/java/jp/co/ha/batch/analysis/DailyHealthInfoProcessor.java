package jp.co.ha.batch.analysis;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import jp.co.ha.business.io.file.csv.model.DailyHealthInfoCsvModel;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 日次健康情報データ分析連携バッチ-Proccesor
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyHealthInfoProcessor
        implements ItemProcessor<HealthInfo, DailyHealthInfoCsvModel> {

    /** 日付フォーマット(yyyy/MM/dd HH:mm:ss) */
    private static final DateFormatType FORMAT_TYPE = DateFormatType.YYYYMMDDHHMMSS;

    @Override
    public DailyHealthInfoCsvModel process(HealthInfo item) throws Exception {

        DailyHealthInfoCsvModel model = new DailyHealthInfoCsvModel();
        model.setSeqHealthInfoId(item.getSeqHealthInfoId());
        model.setSeqUserId(item.getSeqUserId());
        model.setHeight(item.getHeight());
        model.setWeight(item.getWeight());
        model.setBmi(item.getBmi());
        model.setStandardWeight(item.getStandardWeight());
        model.setHealthInfoRegDate(
                DateTimeUtil.toString(item.getHealthInfoRegDate(), FORMAT_TYPE));

        return model;
    }

}
