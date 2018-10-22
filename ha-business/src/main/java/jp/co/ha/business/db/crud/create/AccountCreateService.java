package jp.co.ha.business.db.crud.create;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;

/**
 * アカウント作成サービスインターフェース<br>
 *
 */
public interface AccountCreateService {

	void create(Account entity) throws BaseException;
}
