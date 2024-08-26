package jp.co.ha.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Decimal;

/**
 * 浮動小数の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Decimal
 * @version 1.0.0
 */
public class DecimalValidator implements ConstraintValidator<Decimal, Object> {

    /** 最小桁数 */
    private int min;
    /** 最大桁数 */
    private int max;
    /** 最小桁数で同値含むか */
    private boolean minEqual;
    /** 最大桁数で同値含むか */
    private boolean maxEqual;

    @Override
    public void initialize(Decimal annotation) {
        this.min = annotation.min();
        this.max = annotation.max();
        this.minEqual = annotation.minEqual();
        this.maxEqual = annotation.maxEqual();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (BeanUtil.isNull(value) || StringUtil.isEmpty(value.toString())) {
            return true;
        }
        if (RegexType.DECIMAL.is(value.toString())) {
            int length = value.toString().replace(".", "").length();
            if (minEqual && maxEqual) {
                return (min <= length) && (length <= max);
            } else if (minEqual) {
                return (min <= length) && (length < max);
            } else if (maxEqual) {
                return (min < length) && (length <= max);
            } else {
                return (min < length) && (length < max);
            }
        } else {
            return false;
        }
    }

}
