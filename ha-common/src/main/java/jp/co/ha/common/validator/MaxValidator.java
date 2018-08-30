package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Max;

/**
 * 最大桁数チェックvalidator<br>
 *
 */
public class MaxValidator implements ConstraintValidator<Max, String> {

	private int size;

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
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtil.isEmpty(value)) {
			return true;
		}
		if (isEqual) {
			return value.length() <= this.size;
		} else {
			return value.length() < this.size;
		}

	}

}
