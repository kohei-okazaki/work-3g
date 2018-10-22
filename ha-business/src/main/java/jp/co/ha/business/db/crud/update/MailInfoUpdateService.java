package jp.co.ha.business.db.crud.update;

import jp.co.ha.business.db.entity.MailInfo;
import jp.co.ha.common.exception.BaseException;

/**
 * メール情報更新サービスインターフェース<br>
 *
 */
public interface MailInfoUpdateService {

	void update(MailInfo mailInfo) throws BaseException;
}
