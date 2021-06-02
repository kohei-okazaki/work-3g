package jp.co.ha.batch.ｍonthlyHealthInfoSummary;

import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;

/**
 * 月次健康情報集計バッチ妥当性チェッククラス
 *
 * @version 1.0.0
 */
@Component
public class MonthlyHealthInfoSummaryValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {

        Map<String, JobParameter> params = parameters.getParameters();
        String targetValue = params.get("m").getValue().toString();
        if (StringUtil.isEmpty(targetValue)) {
            return;
        } else if (!DateTimeUtil.isDate(targetValue, DateFormatType.YYYYMM_NOSEP)) {
            // YYYYMM形式でない場合
            throw new JobParametersInvalidException(
                    "job parameter invalid m=" + targetValue);
        }
    }

}
