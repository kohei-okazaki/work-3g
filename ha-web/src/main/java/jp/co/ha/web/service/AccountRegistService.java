package jp.co.ha.web.service;

import jp.co.ha.common.entity.Account;
import jp.co.ha.web.form.AccountRegistForm;

/**
 * アカウント登録サービスインターフェース
 *
 */
public interface AccountRegistService {

	/**
	 * アカウントEntityに変換する<br>
	 * @param form AccountRegistForm
	 * @return
	 */
	Account toAccount(AccountRegistForm form);

	/**
	 * 指定したアカウント情報が有効かどうか判定する<br>
	 * 作成できないアカウント情報の場合true, それ以外の場合false<br>
	 * @return AccountRegistForm
	 */
	boolean invalidUserId(AccountRegistForm form);

}
