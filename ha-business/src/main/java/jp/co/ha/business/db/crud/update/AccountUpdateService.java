package jp.co.ha.business.db.crud.update;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;

/**
 * アカウント情報更新サービスインターフェース
 * 
 * @since 1.0
 */
public interface AccountUpdateService {

	/**
	 * 指定したアカウント情報を更新する
	 *
	 * @param entity
	 *     アカウント情報
	 * @throws BaseException
	 *     基底例外
	 */
	void update(Account entity) throws BaseException;
}
