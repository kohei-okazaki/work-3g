package jp.co.ha.common.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Flag;

/**
 * フラグチェックvalidator<br>
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
		return List.of(StringUtil.FALSE_FLAG, StringUtil.TRUE_FLAG).contains(value);
	}

}
