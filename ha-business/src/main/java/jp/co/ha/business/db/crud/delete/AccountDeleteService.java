package jp.co.ha.business.db.crud.delete;

import jp.co.ha.common.exception.BaseException;

/**
 * アカウント削除サービスインターフェース
 * 
 * @since 1.0
 */
public interface AccountDeleteService {

	/**
	 * 指定されたユーザIDのアカウント情報を削除する
	 *
	 * @param userId
	 *     ユーザID
	 * @throws BaseException
	 *     基底例外
	 */
	void deleteByUserId(String userId) throws BaseException;
}
