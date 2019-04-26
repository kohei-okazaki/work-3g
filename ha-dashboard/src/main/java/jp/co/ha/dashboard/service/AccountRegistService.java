package jp.co.ha.dashboard.service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.dashboard.form.AccountRegistForm;

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
