package jp.co.ha.business.db.crud.read;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;

/**
 * メール情報検索サービスインターフェース
 *
 */
public interface MailInfoSearchService {

	/**
	 * ユーザIDからメール情報を取得する
	 *
	 * @param userId
	 *     ユーザID
	 * @return メール情報
	 * @throws BaseException
	 *     基底例外
	 */
	MailInfo findByUserId(String userId) throws BaseException;

}
