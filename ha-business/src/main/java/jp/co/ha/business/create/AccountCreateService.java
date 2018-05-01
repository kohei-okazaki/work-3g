package jp.co.ha.business.create;

import jp.co.ha.common.entity.Account;

/**
 * アカウント作成サービス<br>
 *
 */
public interface AccountCreateService {

	/**
	 * アカウントを作成する<br>
	 * @param account
	 */
	void create(Account account);
}
