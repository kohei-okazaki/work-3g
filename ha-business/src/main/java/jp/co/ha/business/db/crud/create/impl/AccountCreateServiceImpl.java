package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.AccountCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.mapper.AccountMapper;

/**
 * アカウント情報作成サービスインターフェース実装クラス
 *
 */
@Service
public class AccountCreateServiceImpl implements AccountCreateService {

	@Autowired
	private AccountMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Insert
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void create(Account entity) throws BaseException {
		mapper.insert(entity);
	}

}
