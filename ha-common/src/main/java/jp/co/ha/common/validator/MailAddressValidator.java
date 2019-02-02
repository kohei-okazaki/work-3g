package jp.co.ha.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.validator.annotation.MailAddress;

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
		return RegixType.MAIL_ADDRESS.is().test(value);
	}
}
