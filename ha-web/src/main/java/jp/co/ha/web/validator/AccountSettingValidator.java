package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.web.BaseValidator;
import jp.co.ha.web.form.AccountSettingForm;

/**
 * アカウント設定Validateクラス<br>
 *
 */
public class AccountSettingValidator extends BaseValidator<AccountSettingForm> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {

		AccountSettingForm form = (AccountSettingForm) target;

//		// 必須チェック
//		checKRequire(errors);
//		// 最大桁数チェック
//		checkMaxLength(errors, form);
	}

	/**
	 * 桁数超過チェックを行う<br>
	 * @param errors
	 * @param form
	 */
	private void checkMaxLength(Errors errors, AccountSettingForm form) {

		rejectIfLengthMax(errors, form.getDeleteFlag(), 1);
		rejectIfLengthMax(errors, form.getUserId(), 16);
		rejectIfLengthMax(errors, form.getPassword(), 16);
	}

	/**
	 * 必須チェックを行う<br>
	 * @param errors
	 */
	private void checKRequire(Errors errors) {

		rejectIfEmpty(errors, "deleteFlag");
		rejectIfEmpty(errors, "userId");
		rejectIfEmpty(errors, "password");
		rejectIfEmpty(errors, "fileEnclosureCharFlag");
		rejectIfEmpty(errors, "mailAddress");
		rejectIfEmpty(errors, "mailPassword");
	}

}
