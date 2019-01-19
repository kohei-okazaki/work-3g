package jp.co.ha.web.service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.form.AccountRegistForm;

/**
 * アカウント登録サービスインターフェース
 *
 */
public interface AccountRegistService {

	/**
	 * 登録処理を行う
	 *
	 * @param form
	 *     アカウント登録画面フォーム
	 * @throws BaseException
	 *     基底例外
	 */
	void regist(AccountRegistForm form) throws BaseException;

}
