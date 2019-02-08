package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Min;

/**
 * 最小桁数チェックvalidator
 *
 */
public class MinValidator implements ConstraintValidator<Min, String> {

	private int size;

	private boolean isEqual;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Min annotation) {
		this.size = annotation.size();
		this.isEqual = annotation.isEqual();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtil.isEmpty(value)) {
			return true;
		}
		if (isEqual) {
			return this.size <= value.length();
		} else {
			return this.size < value.length();
		}
	}
}
