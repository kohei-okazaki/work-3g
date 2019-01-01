package jp.co.ha.business.db.crud.update;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;

/**
 * メール情報更新サービスインターフェース
 *
 */
public interface MailInfoUpdateService {

	/**
	 * メール情報を更新する
	 *
	 * @param mailInfo
	 *     メール情報
	 * @throws BaseException
	 *     基底例外
	 */
	void update(MailInfo mailInfo) throws BaseException;
}
