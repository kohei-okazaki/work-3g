package jp.co.ha.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Required;

/**
 * 必須妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Required
 * @version 1.0.0
 */
public class RequiredValidator implements ConstraintValidator<Required, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (BeanUtil.isNull(value)) {
            return false;
        }
        return !StringUtil.isEmpty(value.toString());
    }

}
