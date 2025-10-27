package jp.co.ha.batch.healthInfoMigrate;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;

/**
 * 健康情報連携バッチ妥当性チェッククラス
 *
 * @version 1.0.0
 */
@Component
public class HealthInfoMigrateValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {

        String targetValue = parameters.getParameters().get("d").getValue().toString();
        if (!StringUtil.isEmpty(targetValue)
                && !DateTimeUtil.isDate(targetValue, DateFormatType.YYYYMMDD_NOSEP)) {
            // 指定ありかつYYYYMMDD形式でない場合
            throw new JobParametersInvalidException(
                    "job parameter invalid d=" + targetValue);
        }
    }

}
