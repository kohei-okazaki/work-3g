package jp.co.ha.business.create.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.create.AccountCreateService;
import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.entity.Account;

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
	public void create(Account entity) {
		accountDao.create(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(List<Account> entityList) {
		entityList.forEach(entity -> accountDao.create(entity));
	}

}
