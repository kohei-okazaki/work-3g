package jp.co.ha.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validate.annotation.MailAddress;

/**
 * メールアドレス形式チェックvalidator<br>
 *
 */
public class MailAddressValidator implements ConstraintValidator<MailAddress, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtil.isEmpty(value)) {
			return true;
		}
		return false;
	}

}
