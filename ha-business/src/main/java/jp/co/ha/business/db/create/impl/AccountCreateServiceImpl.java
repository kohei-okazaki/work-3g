package jp.co.ha.business.db.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.create.AccountCreateService;
import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.exception.DataBaseException;

/**
 * アカウント情報作成サービスインターフェース実装クラス<br>
 *
 */
@Service
public class AccountCreateServiceImpl implements AccountCreateService {

	/** アカウント情報Dao */
	@Autowired
	private AccountDao accountDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(Account entity) throws DataBaseException {
		accountDao.create(entity);
	}

}
