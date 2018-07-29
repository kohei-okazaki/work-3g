package jp.co.ha.web.validator;

import org.springframework.validation.Errors;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.BaseWebValidator;
import jp.co.ha.web.form.AccountRegistForm;

/**
 * アカウント登録画面Validateクラス
 *
 */
public class AccountRegistValidator extends BaseWebValidator<AccountRegistForm> {

	/** アカウント検索サービス */
	private AccountSearchService accountSearchService;

	/**
	 * アカウント検索サービスを設定する<br>
	 *
	 * @param accountSearchService
	 *     アカウント検索サービス
	 */
	public void setAccountSearchService(AccountSearchService accountSearchService) {
		this.accountSearchService = accountSearchService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object object, Errors errors) {
		AccountRegistForm form = (AccountRegistForm) object;
		try {
			checkExistAccount(errors, form);
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * アカウントがすでに存在する場合validateエラーにする<br>
	 *
	 * @param errors
	 *     エラー
	 * @param form
	 *     アカウント情報登録form
	 * @throws BaseAppException
	 */
	private void checkExistAccount(Errors errors, AccountRegistForm form) throws BaseAppException {
		Account account = accountSearchService.findByUserId(form.getUserId());
		if (BeanUtil.notNull(account)) {
			errors.rejectValue("userId", "validate.message.existAccount");
		}
	}

}
