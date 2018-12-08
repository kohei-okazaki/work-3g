package jp.co.ha.business.db.crud.create;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;

/**
 * アカウント作成サービスインターフェース<br>
 *
 */
public interface AccountCreateService {

	/**
	 * 指定したアカウント情報を登録する<br>
	 *
	 * @param entity
	 *     アカウント情報
	 * @throws BaseException
	 *     基底例外
	 */
	void create(Account entity) throws BaseException;
}
