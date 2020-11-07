package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Min;

/**
 * 最小桁数の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Min
 * @version 1.0.0
 */
public class MinValidator implements ConstraintValidator<Min, Object> {

    /** 桁数 */
    private int size;
    /** 同じ値を含むかどうか */
    private boolean isEqual;

    @Override
    public void initialize(Min annotation) {
        this.size = annotation.size();
        this.isEqual = annotation.isEqual();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (BeanUtil.isNull(value) || StringUtil.isEmpty(value.toString())) {
            return true;
        }
        if (isEqual) {
            return this.size <= value.toString().length();
        } else {
            return this.size < value.toString().length();
        }
    }

}
