package jp.co.ha.business.db.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.dao.AccountDao;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.business.db.update.AccountUpdateService;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント情報更新サービス実装クラス<br>
 *
 */
@Service
public class AccountUpdateServiceImpl implements AccountUpdateService {

	/** アカウントDao */
	@Autowired
	private AccountDao accountDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Account account) throws BaseException {
		accountDao.update(account);
	}

}
