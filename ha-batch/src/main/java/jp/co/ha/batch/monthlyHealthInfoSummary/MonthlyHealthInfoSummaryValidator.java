package jp.co.ha.batch.monthlyHealthInfoSummary;

import java.util.Optional;

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

        String targetValue = Optional.ofNullable(parameters.getString("m")).orElse("");
        if (!StringUtil.isEmpty(targetValue)
                && !DateTimeUtil.isDate(targetValue, DateFormatType.YYYYMM_NOSEP)) {
            // 指定ありかつYYYYMM形式でない場合
            throw new JobParametersInvalidException(
                    "job parameter invalid m=" + targetValue);
        }
    }

}
