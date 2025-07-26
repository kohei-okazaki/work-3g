package jp.co.ha.common.validator;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jp.co.ha.common.validator.annotation.Past;

/**
 * 過去日の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Past
 * @version 1.0.0
 */
public class PastValidator implements ConstraintValidator<Past, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            // 必須チェックは@Requiredで行う
            return true;
        }
        return value.isBefore(LocalDate.now());
    }

}
