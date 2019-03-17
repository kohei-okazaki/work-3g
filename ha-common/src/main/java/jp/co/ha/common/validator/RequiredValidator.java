package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Required;

/**
 * 文字列型の必須妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Required
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
