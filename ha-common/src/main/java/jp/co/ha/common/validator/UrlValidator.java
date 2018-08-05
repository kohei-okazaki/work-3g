package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.RegixPattern;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validate.annotation.Url;

/**
 * URLチェックvalidator<br>
 *
 */
public class UrlValidator implements ConstraintValidator<Url, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (StringUtil.isEmpty(value)) {
			return true;
		}

		String urlRegix = RegixPattern.URL.getPattern();
		return value.matches(urlRegix);
	}

}
