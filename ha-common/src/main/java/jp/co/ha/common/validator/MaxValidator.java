package jp.co.ha.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Max;

/**
 * 最大桁数の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Max
 * @version 1.0.0
 */
public class MaxValidator implements ConstraintValidator<Max, Object> {

    /** 桁数 */
    private long size;
    /** 同じ値を含むかどうか */
    private boolean isEqual;

    @Override
    public void initialize(Max annotation) {
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
            return longValue <= size;
        } else {
            return longValue < size;
        }
    }

}
