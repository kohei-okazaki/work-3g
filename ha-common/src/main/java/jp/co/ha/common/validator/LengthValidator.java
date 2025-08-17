package jp.co.ha.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Length;

/**
 * 桁数の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Length
 * @version 1.0.0
 */
public class LengthValidator implements ConstraintValidator<Length, Object> {

    /** length */
    private int length;
    /** mode */
    private LengthMode mode;

    @Override
    public void initialize(Length annotation) {
        this.length = annotation.length();
        this.mode = annotation.mode();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (BeanUtil.isNull(value) || StringUtil.isEmpty(value.toString())) {
            return true;
        }

        int actualLength = value.toString().length();

        switch (mode) {
        case EQUAL:
            // 一致
            return actualLength == length;

        case LESS_EQUAL:
            // 以下
            return actualLength <= length;

        case GREATER_EQUAL:
            // 以上
            return actualLength >= length;

        case LESS_THAN:
            // 未満
            return actualLength < length;

        case GREATER_THAN:
            // より大きい
            return actualLength > length;

        default:
            return false;
        }
    }

}
