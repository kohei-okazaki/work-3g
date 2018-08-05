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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Max annotation) {
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
		 return value.length() < this.size;
	}

}
