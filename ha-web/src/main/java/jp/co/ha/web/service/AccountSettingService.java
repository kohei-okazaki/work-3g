package jp.co.ha.web.service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.form.AccountSettingForm;

/**
 * アカウント設定サービスインターフェース
 *
 */
public interface AccountSettingService {

	/**
	 * アカウント設定のメイン処理を行う
	 *
	 * @param form
	 *     アカウント設定画面フォーム
	 * @throws BaseException
	 *     基底例外
	 */
	void execute(AccountSettingForm form) throws BaseException;

}
