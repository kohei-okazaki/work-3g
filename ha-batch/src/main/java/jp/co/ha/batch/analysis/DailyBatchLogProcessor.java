package jp.co.ha.batch.analysis;

import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import jp.co.ha.business.io.file.csv.model.DailyBatchLogCsvModel;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.custom.CustomJobData;

/**
 * 日次API通信ログデータ分析連携バッチ-Processor
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyBatchLogProcessor
        implements ItemProcessor<CustomJobData, DailyBatchLogCsvModel> {

    @Override
    public DailyBatchLogCsvModel process(CustomJobData item) throws Exception {

        DailyBatchLogCsvModel model = new DailyBatchLogCsvModel();

        model.setJobInstanceId(item.getJobInstanceId());
        model.setStartDate(DateTimeUtil.toString(item.getStartTime(), YYYYMMDDHHMMSS));
        model.setEndDate(DateTimeUtil.toString(item.getEndTime(), YYYYMMDDHHMMSS));
        model.setJobName(item.getJobName());

        return model;
    }

}
