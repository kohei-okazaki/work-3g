package jp.co.ha.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.BaseValidator;
import jp.co.ha.web.form.LoginForm;

/**
 * ログイン画面のValidateクラス<br>
 *
 */
public class LoginValidator extends BaseValidator<LoginForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return LoginForm.class.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {

		LoginForm form = (LoginForm) target;
		// 必須チェック
		checkRequire(errors);
		// 桁数チェック
		checkLength(form, errors);
		// 属性チェック
		checkType(form, errors);
	}

	/**
	 * 属性チェックを行う<br>
	 * @param form
	 * @param errors
	 */
	private void checkType(LoginForm form, Errors errors) {

		if (!StringUtil.isHalfChar(form.getUserId()) || !StringUtil.isHalfChar(form.getPassword())) {
			// 半角英数字であるない場合
			errors.rejectValue("userId", ErrorCode.TYPE.toString());
		}
	}

	/**
	 * 桁数チェックを行う<br>
	 * @param form
	 * @param errors
	 */
	private void checkLength(LoginForm form, Errors errors) {

		if (!(2 < form.getUserId().length() && form.getUserId().length() <= 10)) {
			ValidationUtils.rejectIfEmpty(errors, "userId", ErrorCode.LENGTH.toString());
		}
	}

	/**
	 * 必須チェックを行う<br>
	 * @param errors
	 */
	private void checkRequire(Errors errors) {

		ValidationUtils.rejectIfEmpty(errors, "userId", ErrorCode.REQUIRE.toString());
		ValidationUtils.rejectIfEmpty(errors, "password", ErrorCode.REQUIRE.toString());
	}

}
