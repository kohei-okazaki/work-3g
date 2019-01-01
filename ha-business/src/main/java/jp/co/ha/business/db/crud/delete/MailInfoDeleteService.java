package jp.co.ha.business.db.crud.delete;

import jp.co.ha.common.exception.BaseException;

/**
 * メール情報削除サービスインターフェース
 *
 */
public interface MailInfoDeleteService {

	/**
	 * 指定されたユーザIDのメール情報を削除する
	 *
	 * @param userId
	 *     ユーザID
	 * @throws BaseException
	 *     基底例外
	 */
	void deleteByUserId(String userId) throws BaseException;

}
