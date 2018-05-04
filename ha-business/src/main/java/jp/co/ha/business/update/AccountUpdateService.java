package jp.co.ha.business.update;

import jp.co.ha.common.entity.Account;

/**
 * アカウント情報更新サービスインターフェース<br>
 *
 */
public interface AccountUpdateService {

	/**
	 * アカウントを更新する<br>
	 * @param account
	 */
	void update(Account account);
}
