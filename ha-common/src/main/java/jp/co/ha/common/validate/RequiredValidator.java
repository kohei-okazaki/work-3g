package jp.co.ha.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validate.annotation.Required;

/**
 * 必須チェックvalidator<br>
 *
 */
public class RequiredValidator implements ConstraintValidator<Required, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !StringUtil.isEmpty(value);
	}

}
