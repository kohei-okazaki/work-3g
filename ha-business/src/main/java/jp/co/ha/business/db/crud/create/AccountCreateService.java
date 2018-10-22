package jp.co.ha.business.db.crud.create;

import jp.co.ha.business.db.entity.Account;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント作成サービスインターフェース<br>
 *
 */
public interface AccountCreateService {

	void create(Account entity) throws BaseException;
}
