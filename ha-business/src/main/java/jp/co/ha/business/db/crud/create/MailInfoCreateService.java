package jp.co.ha.business.db.crud.create;

import jp.co.ha.business.db.entity.MailInfo;
import jp.co.ha.common.exception.BaseException;

/**
 * メール情報作成サービスインターフェース<br>
 *
 */
public interface MailInfoCreateService {

	void create(MailInfo entity) throws BaseException;
}
