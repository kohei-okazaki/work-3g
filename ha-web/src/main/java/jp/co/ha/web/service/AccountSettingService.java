package jp.co.ha.web.service;

import jp.co.ha.common.entity.MailInfo;
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
	 *            AccountSettingForm
	 */
	void execute(AccountSettingForm form);

	/**
	 * 指定されたアカウント情報の削除をする<br>
	 *
	 * @param form
	 *            AccountSettingForm
	 */
	void deleteAccount(AccountSettingForm form);

	/**
	 * フォーム情報をメール情報に変換する<br>
	 *
	 * @param form
	 *            AccountSettingForm
	 * @return
	 */
	MailInfo convertMailInfo(AccountSettingForm form);

	/**
	 * フォーム情報をメール情報にマージする<br>
	 *
	 * @param mailInfo
	 *            MailInfo
	 * @param form
	 *            AccountSettingForm
	 * @return
	 */
	void mergeMailInfo(MailInfo mailInfo, AccountSettingForm form);

}
