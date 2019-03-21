package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Flag;

/**
 * フラグの妥当性チェックvalidator
 *
 * @see jp.co.ha.common.validator.annotation.Flag
 *
 */
public class FlagValidator implements ConstraintValidator<Flag, Object> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (BeanUtil.isNull(value) || StringUtil.isEmpty(value.toString())) {
			return true;
		}
		return RegixType.FLAG.is().test(value.toString());
	}
}
