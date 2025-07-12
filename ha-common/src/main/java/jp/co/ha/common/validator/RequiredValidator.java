package jp.co.ha.common.validator;

import java.util.Collection;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
        if (value == null) {
            return false;
        }

        if (value instanceof String str) {
            return !str.isBlank();
        }

        if (value instanceof Collection<?> collection) {
            return !collection.isEmpty();
        }

        // 他の型は null でなければ OK
        return true;
    }

}
