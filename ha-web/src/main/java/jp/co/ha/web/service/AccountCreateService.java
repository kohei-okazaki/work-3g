package jp.co.ha.web.service;

import jp.co.ha.common.entity.Account;
import jp.co.ha.web.form.AccountCreateForm;

/**
 * アカウント作成サービスインターフェース
 *
 */
public interface AccountCreateService {

	/**
	 * アカウントEntityに変換する<br>
	 * @param form
	 * @return
	 */
	Account toAccount(AccountCreateForm form);

	/**
	 * 指定したアカウント情報が有効かどうか判定する<br>
	 * 作成できないアカウント情報の場合true, それ以外の場合false<br>
	 * @return
	 */
	boolean invalidUserId(AccountCreateForm form);

}
