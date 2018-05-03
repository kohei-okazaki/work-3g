package jp.co.ha.business.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.update.AccountUpdateService;
import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.entity.Account;

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
	public void update(Account account) {
		accountDao.updateAccount(account);
	}

}
