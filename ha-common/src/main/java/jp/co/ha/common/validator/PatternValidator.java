package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.Pattern;

/**
 * 型チェックvalidator
 *
 */
public class PatternValidator implements ConstraintValidator<Pattern, String> {

	private RegixType regix;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Pattern annotation) {
		this.regix = annotation.regixPattern();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtil.isEmpty(value)) {
			return true;
		}
		return this.regix.is().test(value);
	}
}
