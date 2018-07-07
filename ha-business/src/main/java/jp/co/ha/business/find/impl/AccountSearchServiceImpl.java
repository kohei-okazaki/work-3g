package jp.co.ha.business.find.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.entity.Account;

/**
 * アカウント情報検索サービスインターフェース実装クラス<br>
 *
 */
@Service
public class AccountSearchServiceImpl implements AccountSearchService {

	/** アカウント情報Dao */
	@Autowired
	private AccountDao accountDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account findByUserId(String userId) {
		return accountDao.selectByUserId(userId);
	}

}
