package jp.co.ha.business.db.crud.update;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;

/**
 * メール情報更新サービスインターフェース<br>
 *
 */
public interface MailInfoUpdateService {

	void update(MailInfo mailInfo) throws BaseException;
}
