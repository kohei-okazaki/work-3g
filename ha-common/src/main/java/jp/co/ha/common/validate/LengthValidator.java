package jp.co.ha.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.validate.annotation.Length;

/**
 * 桁数チェックvalidator<br>
 *
 */
public class LengthValidator implements ConstraintValidator<Length, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Length length;
		int min;
		int max;
		return false;
	}

}
