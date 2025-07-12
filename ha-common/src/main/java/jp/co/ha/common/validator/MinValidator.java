package jp.co.ha.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
    private long size;
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
        if (!(value instanceof Number)) {
            // 数値型以外はチェックできないのでtrueまたはfalseにする
            return false;
        }
        long longValue = ((Number) value).longValue();

        if (isEqual) {
            return longValue >= size;
        } else {
            return longValue > size;
        }
    }

}
