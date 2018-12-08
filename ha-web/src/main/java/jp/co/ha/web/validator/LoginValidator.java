package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.web.validator.BaseWebValidator;
import jp.co.ha.web.form.LoginForm;

/**
 * ログイン画面のValidateクラス<br>
 *
 */
public class LoginValidator extends BaseWebValidator<LoginForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		LoginForm form = (LoginForm) target;
	}

}
