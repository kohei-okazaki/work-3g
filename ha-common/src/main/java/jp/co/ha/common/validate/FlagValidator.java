package jp.co.ha.common.validate;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validate.annotation.Flag;

/**
 * フラグチェックvalidator<br>
 *
 */
public class FlagValidator implements ConstraintValidator<Flag, String> {

	private static final List<String> FLAG_LIST = List.of(StringUtil.FALSE_FLAG, StringUtil.TRUE_FLAG);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtil.isEmpty(value)) {
			return true;
		}
		return FLAG_LIST.contains(value);
	}

}
