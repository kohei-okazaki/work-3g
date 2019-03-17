package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Length;

/**
 * 桁数の妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Length
 *
 */
public class LengthValidator implements ConstraintValidator<Length, String> {

	/** length */
	private int length;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Length annotation) {
		this.length = annotation.length();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtil.isEmpty(value)) {
			 return true;
		}
		return value.length() == length;
	}

}
