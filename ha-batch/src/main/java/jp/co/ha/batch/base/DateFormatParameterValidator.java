package jp.co.ha.batch.base;

import java.util.Optional;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;

/**
 * SpringBatchのJob引数の妥当性チェッククラス
 * 
 * @version 1.0.0
 */
public class DateFormatParameterValidator implements JobParametersValidator {

    /** バッチ引数キー */
    private final String key;
    /** 日付フォーマット */
    private final DateFormatType formatType;
    /** 必須有無 */
    private final boolean required;

    /**
     * コンストラクタ
     * 
     * @param key
     *     バッチ引数キー
     * @param formatType
     *     日付フォーマット
     * @param required
     *     必須有無
     */
    public DateFormatParameterValidator(String key, DateFormatType formatType,
            boolean required) {
        this.key = key;
        this.formatType = formatType;
        this.required = required;
    }

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String value = Optional.ofNullable(parameters.getString(key)).orElse("");

        if (required && StringUtil.isEmpty(value)) {
            throw new JobParametersInvalidException("job parameter required " + key);
        } else if (!StringUtil.isEmpty(value)
                && !DateTimeUtil.isDate(value, formatType)) {
            throw new JobParametersInvalidException(
                    "job parameter invalid " + key + "=" + value);
        }
    }
}
