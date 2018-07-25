package jp.co.ha.business.find;

import jp.co.ha.common.entity.Account;
import jp.co.ha.common.exception.BaseAppException;

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
	 * @throws BaseAppException
	 */
	Account findByUserId(String userId) throws BaseAppException;

}
