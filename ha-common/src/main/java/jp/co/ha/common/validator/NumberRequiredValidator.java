package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.NumberRequired;

/**
 * 数値型の必須妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.NumberRequired
 *
 */
public class NumberRequiredValidator implements ConstraintValidator<NumberRequired, Object> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (BeanUtil.isNull(value)) {
			return false;
		}
		return !StringUtil.isEmpty(value.toString());
	}
}
