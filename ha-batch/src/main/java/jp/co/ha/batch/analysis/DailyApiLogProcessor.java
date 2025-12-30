package jp.co.ha.batch.analysis;

import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import jp.co.ha.business.io.file.csv.model.DailyApiLogCsvModel;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.ApiLog;

/**
 * 日次API通信ログデータ分析連携バッチ-Processor
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyApiLogProcessor implements ItemProcessor<ApiLog, DailyApiLogCsvModel> {

    @Override
    public DailyApiLogCsvModel process(ApiLog item) throws Exception {

        DailyApiLogCsvModel model = new DailyApiLogCsvModel();

        model.setSeqApiLogId(item.getSeqApiLogId());
        model.setTransactionId(item.getTransactionId());
        model.setHttpMethod(item.getHttpMethod());
        model.setUrl(item.getUrl());
        model.setBody(item.getBody());
        model.setRequestDate(
                DateTimeUtil.toString(item.getRequestDate(), YYYYMMDDHHMMSS));
        model.setHttpStatus(String.valueOf(item.getHttpStatus()));
        model.setResponseDate(
                DateTimeUtil.toString(item.getResponseDate(), YYYYMMDDHHMMSS));

        return model;
    }

}
