package jp.co.ha.business.db.crud.read;

import java.util.Optional;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;

/**
 * メール情報検索サービスインターフェース
 * 
 * @since 1.0
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
	Optional<MailInfo> findByUserId(String userId) throws BaseException;

}
