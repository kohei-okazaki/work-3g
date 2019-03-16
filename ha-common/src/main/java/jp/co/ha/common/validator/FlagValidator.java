package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Flag;

/**
 * フラグの妥当性チェッククラス
 *
 * @see jp.co.ha.common.validator.annotation.Flag
 *
 */
public class FlagValidator implements ConstraintValidator<Flag, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtil.isEmpty(value)) {
			return true;
		}
		return RegixType.FLAG.is().test(value);
	}
}
