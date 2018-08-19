package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Min;

/**
 * 最小桁数チェックvalidator<br>
 *
 */
public class MinValidator implements ConstraintValidator<Min, String> {

	private int size;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Min annotation) {
		this.size = annotation.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtil.isEmpty(value)) {
			return true;
		}
		return this.size <= value.length();
	}

}
