package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.validator.BaseWebValidator;
import jp.co.ha.db.entity.Account;
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

	/**
	 * アカウントが存在しない場合validateエラーにする<br>
	 *
	 * @param errors
	 *     エラー
	 * @param account
	 *     アカウント情報
	 */
	private void checkExistAccount(Errors errors, Account account) {
		if (BeanUtil.isNull(account)) {
			errors.rejectValue("userId", "validate.message.notExistAccount");
		}
	}

	/**
	 * ログイン情報と入力情報を照合する<br>
	 *
	 * @param errors
	 *     エラー
	 * @param formPassword
	 *     フォーム情報.パスワード
	 * @param dbPassword
	 *     DB情報.パスワード
	 */
	private void checkInvalidPassword(Errors errors, String formPassword, String dbPassword) {
		if (!formPassword.equals(dbPassword)) {
			errors.rejectValue("userId", "validate.message.invalidPassword");
		}
	}

	/**
	 * アカウント情報が有効かどうかチェック<br>
	 * 有効でない場合true, そうでない場合false<br>
	 *
	 * @param errors
	 *     エラー
	 * @param account
	 *     アカウント情報
	 */
	private void checkDeleteAccount(Errors errors, Account account) {
		if (StringUtil.isTrue(account.getDeleteFlag())) {
			errors.rejectValue("userId", "validate.message.invalidPassword");
		}
	}

	/**
	 * アカウント情報が有効期限切れかどうか判定する<br>
	 * アカウント情報.パスワード有効期限 < システム日付の場合、true<br>
	 *
	 * @param errors
	 *     エラー
	 * @param account
	 *     アカウント情報
	 */
	private void checkAccountExpired(Errors errors, Account account) {
		if (DateUtil.isAfter(account.getPasswordExpire(), false)) {
			errors.rejectValue("userId", "validate.message.invalidPassword");
		}
	}

}
