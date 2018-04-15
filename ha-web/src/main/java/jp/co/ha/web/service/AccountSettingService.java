package jp.co.ha.web.service;

import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.MailInfo;
import jp.co.ha.web.form.AccountSettingForm;

/**
 * アカウント設定サービスインターフェース<br>
 *
 */
public interface AccountSettingService {

	/**
	 * 更新処理を行う<br>
	 * @param account
	 * @param mainlInfo
	 */
	void update(Account account, MailInfo mainlInfo);

	/**
	 * 指定されたアカウント情報の削除をする<br>
	 * @param form
	 */
	void deleteAccount(AccountSettingForm form);

	/**
	 * フォーム情報をアカウント情報にマージする<br>
	 * @param account
	 * @param form
	 * @return
	 */
	Account mergeAccount(Account account, AccountSettingForm form);

	/**
	 * フォーム情報をメール情報に変換する<br>
	 * @param form
	 * @return
	 */
	MailInfo convertMailInfo(AccountSettingForm form);

	/**
	 * アカウント情報を更新する<br>
	 * @param account
	 */
	void updateAccount(Account account);

	/**
	 * フォーム情報をメール情報にマージする<br>
	 * @param mailInfo
	 * @param form
	 * @return
	 */
	MailInfo mergeMailInfo(MailInfo mailInfo, AccountSettingForm form);

}
