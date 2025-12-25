package jp.co.ha.batch.analysis;

import java.util.Optional;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;

/**
 * 日次健康情報データ分析連携バッチ妥当性チェッククラス
 *
 * @version 1.0.0
 */
@Component
public class DailyHealthInfoValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {

        String targetValue = Optional.ofNullable(parameters.getString("d")).orElse("");
        if (!StringUtil.isEmpty(targetValue)
                && !DateTimeUtil.isDate(targetValue, DateFormatType.YYYYMMDD_NOSEP)) {
            // 指定ありかつYYYYMMDD形式でない場合
            throw new JobParametersInvalidException(
                    "job parameter invalid d=" + targetValue);
        }
    }
}
