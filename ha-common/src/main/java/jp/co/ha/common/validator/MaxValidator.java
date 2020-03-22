package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Max;

/**
 * 最大桁数の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Max
 * @since 1.0
 */
public class MaxValidator implements ConstraintValidator<Max, Object> {

    /** 桁数 */
    private int size;
    /** 同じ値を含むかどうか */
    private boolean isEqual;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Max annotation) {
        this.size = annotation.size();
        this.isEqual = annotation.isEqual();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (BeanUtil.isNull(value) || StringUtil.isEmpty(value.toString())) {
            return true;
        }
        if (isEqual) {
            return value.toString().length() <= this.size;
        } else {
            return value.toString().length() < this.size;
        }
    }

}
