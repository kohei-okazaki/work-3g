package jp.co.ha.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.service.AccountSearchService;

@Service
public class AccountSearchServiceImpl implements AccountSearchService {

	/** アカウント情報Dao */
	@Autowired
	private AccountDao accountDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account findAccountByUserId(String userId) {
		return this.accountDao.getAccountByUserId(userId);
	}

}
