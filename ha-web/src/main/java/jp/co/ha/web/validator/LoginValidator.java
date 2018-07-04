package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.BaseValidator;
import jp.co.ha.web.form.LoginForm;

/**
 * ログイン画面のValidateクラス<br>
 *
 */
public class LoginValidator extends BaseValidator<LoginForm> {

	/** アカウント検索サービス */
	private AccountSearchService accountSearchService;

	/**
	 * アカウント検索サービスを設定する<br>
	 * @param accountSearchService アカウント検索サービス
	 */
	public void setAccountSearchService(AccountSearchService accountSearchService) {
		this.accountSearchService = accountSearchService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {

		LoginForm form = (LoginForm) target;
		if (StringUtil.isEmpty(form.getUserId())) {
			return;
		}
		Account account = accountSearchService.findByUserId(form.getUserId());
		checkExistAccount(errors, account);
		checkInvalidPassword(errors, form.getPassword(), account.getPassword());
		checkDeleteAccount(errors, account);
	}

	/**
	 * アカウントが存在しない場合validateエラーにする<br>
	 * @param errors
	 * @param form
	 */
	private void checkExistAccount(Errors errors, Account account) {
		if (BeanUtil.isNull(account)) {
			errors.rejectValue("userId", "validate.message.notExistAccount");
		}
	}

	/**
	 * ログイン情報と入力情報を照合する<br>
	 * @param errors
	 * @param formPassword
	 * @param dbPassword
	 */
	private void checkInvalidPassword(Errors errors, String formPassword, String dbPassword) {
		if (!formPassword.equals(dbPassword)) {
			errors.rejectValue("userId", "validate.message.invalidPassword");
		}
	}

	/**
	 * アカウント情報が有効かどうかチェック<br>
	 * 有効でない場合true, そうでない場合false<br>
	 * @param errors
	 * @param account2
	 */
	private void checkDeleteAccount(Errors errors, Account account) {
		if (StringUtil.isTrue(account.getDeleteFlag())) {
			errors.rejectValue("userId", "validate.message.invalidPassword");
		}
	}

}
