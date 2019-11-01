package jp.co.ha.business.db.crud.read;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;

/**
 * アカウント検索サービスインターフェース
 * 
 * @since 1.0
 */
public interface AccountSearchService {

	/**
	 * ユーザIDからアカウント情報を取得する
	 *
	 * @param userId
	 *     ユーザID
	 * @return アカウント情報
	 * @throws BaseException
	 *     基底例外
	 */
	Account findByUserId(String userId) throws BaseException;

}
