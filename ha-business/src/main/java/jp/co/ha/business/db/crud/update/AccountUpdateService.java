package jp.co.ha.business.db.crud.update;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;

/**
 * アカウント情報更新サービスインターフェース<br>
 *
 */
public interface AccountUpdateService {

	/**
	 * 指定したアカウント情報を更新する
	 *
	 * @param account
	 *     アカウント情報
	 * @throws BaseException
	 */
	void update(Account account) throws BaseException;
}
