package jp.co.ha.business.db.crud.update;

import jp.co.ha.business.db.entity.Account;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント情報更新サービスインターフェース<br>
 *
 */
public interface AccountUpdateService {

	void update(Account account) throws BaseException;
}
