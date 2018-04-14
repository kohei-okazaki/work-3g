package jp.co.ha.common.service;

import jp.co.ha.common.entity.Account;

/**
 * アカウント検索サービスインターフェース<br>
 *
 */
public interface AccountSearchService {

	/**
	 * ユーザIDからアカウント情報を取得する
	 * @param userId
	 * @return アカウント情報
	 */
	Account findAccountByUserId(String userId);


}
