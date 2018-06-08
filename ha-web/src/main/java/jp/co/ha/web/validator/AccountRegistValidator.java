package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.web.BaseValidator;
import jp.co.ha.web.form.AccountRegistForm;

/**
 * アカウント作成Validateクラス
 *
 */
public class AccountRegistValidator extends BaseValidator<AccountRegistForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return AccountRegistForm.class.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object object, Errors errors) {

		AccountRegistForm form = (AccountRegistForm) object;

		// 必須チェックを行う
		checkRequire(errors);
		// 最大桁数チェック
		checkMaxLength(errors, form);

	}

	/**
	 * 必須チェックを行う<br>
	 * @param errors
	 */
	private void checkRequire(Errors errors) {

		rejectIfEmpty(errors, "userId");
		rejectIfEmpty(errors, "password");
		rejectIfEmpty(errors, "confirmPassword");
	}

	/**
	 * 桁数超過チェックを行う<br>
	 * @param errors
	 * @param form
	 */
	private void checkMaxLength(Errors errors, AccountRegistForm form) {

		rejectIfLengthMax(errors, form.getUserId(), 16);
		rejectIfLengthMax(errors, form.getPassword(), 16);
		rejectIfLengthMax(errors, form.getConfirmPassword(), 16);
		rejectIfLengthMax(errors, form.getRemarks(), 200);
	}

}
