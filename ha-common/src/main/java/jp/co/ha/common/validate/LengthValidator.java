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

		// MaxValidator
		// if (StringUtil.isEmpty(value)) {
		// 	// 未指定の場合
		// 	return true;
		// }
		// return value.length() < this.size;
		
		// MaxValidator
		// if (StringUtil.isEmpty(value)) {
		// 	// 未指定の場合
		// 	return true;
		// }
		// return this.size < value.length();

		return false;
	}

}
