package jp.co.ha.web.service;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.web.form.AccountSettingForm;

/**
 * アカウント設定サービスインターフェース<br>
 *
 */
public interface AccountSettingService {

	/**
	 * アカウント設定のメイン処理を行う<br>
	 *
	 * @param form
	 *            アカウント設定画面フォーム
	 * @throws BaseAppException
	 */
	void execute(AccountSettingForm form) throws BaseAppException;

}
