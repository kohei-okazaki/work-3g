package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.DateUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Date;

/**
 * 日付の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Date
 * @version 1.0.0
 */
public class DateValidator implements ConstraintValidator<Date, Object> {

    /** 日付フォーマットの列挙 */
    private DateFormatType formatType;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Date annotation) {
        this.formatType = annotation.formatType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (BeanUtil.isNull(value) || StringUtil.isEmpty(value.toString())) {
            return true;
        }

        return DateUtil.isDate(value.toString(), this.formatType);
    }

}
