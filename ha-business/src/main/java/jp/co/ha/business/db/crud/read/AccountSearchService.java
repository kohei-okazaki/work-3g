package jp.co.ha.business.db.crud.read;

import jp.co.ha.business.db.entity.Account;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント検索サービスインターフェース<br>
 *
 */
public interface AccountSearchService {

	/**
	 * ユーザIDからアカウント情報を取得する
	 *
	 * @param userId
	 *     ユーザID
	 * @return アカウント情報
	 * @throws BaseException
	 */
	Account findByUserId(String userId) throws BaseException;

}
