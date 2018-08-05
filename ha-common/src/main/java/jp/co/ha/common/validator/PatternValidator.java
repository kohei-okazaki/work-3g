package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.RegixPattern;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validate.annotation.Pattern;

/**
 * 型チェックvalidator<br>
 *
 */
public class PatternValidator implements ConstraintValidator<Pattern, String> {

	private RegixPattern regix;

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
		return value.matches(this.regix.getPattern());
	}

}
