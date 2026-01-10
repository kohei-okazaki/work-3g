package jp.co.ha.batch.analysis;

import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import jp.co.ha.business.io.file.csv.model.DailyHealthInfoCsvModel;
import jp.co.ha.common.util.DateTimeUtil;
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
                DateTimeUtil.toString(item.getHealthInfoRegDate(), YYYYMMDDHHMMSS));

        return model;
    }

}
