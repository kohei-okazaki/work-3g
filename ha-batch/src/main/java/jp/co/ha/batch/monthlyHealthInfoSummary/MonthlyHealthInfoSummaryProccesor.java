package jp.co.ha.batch.monthlyHealthInfoSummary;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import jp.co.ha.business.io.file.csv.model.MonthlyHealthInfoSummaryModel;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 月次健康情報集計処理-Proccesor
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class MonthlyHealthInfoSummaryProccesor
        implements ItemProcessor<HealthInfo, MonthlyHealthInfoSummaryModel> {

    /** 日付フォーマット(yyyy/MM/dd HH:mm:ss) */
    private static final DateFormatType FORMAT_TYPE = DateFormatType.YYYYMMDDHHMMSS;

    @Override
    public MonthlyHealthInfoSummaryModel process(HealthInfo item) throws Exception {

        MonthlyHealthInfoSummaryModel model = new MonthlyHealthInfoSummaryModel();
        model.setSeqUserId(item.getSeqUserId());
        model.setHeight(item.getHeight());
        model.setWeight(item.getWeight());
        model.setBmi(item.getBmi());
        model.setStandardWeight(item.getStandardWeight());
        model.setHealthInfoRegDate(
                DateTimeUtil.toString(item.getHealthInfoRegDate(), FORMAT_TYPE));
        model.setSeqBmiRangeMtId(item.getSeqBmiRangeMtId());
        model.setRegDate(DateTimeUtil.toString(item.getRegDate(), FORMAT_TYPE));
        model.setUpdateDate(DateTimeUtil.toString(item.getUpdateDate(), FORMAT_TYPE));

        return model;
    }

}
