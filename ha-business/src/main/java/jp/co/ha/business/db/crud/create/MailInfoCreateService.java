package jp.co.ha.business.db.crud.create;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;

/**
 * メール情報作成サービスインターフェース<br>
 *
 */
public interface MailInfoCreateService {

	/**
	 * 指定したメール情報を登録する<br>
	 *
	 * @param entity
	 *     メール情報
	 * @throws BaseException
	 *     基底例外
	 */
	void create(MailInfo entity) throws BaseException;
}
