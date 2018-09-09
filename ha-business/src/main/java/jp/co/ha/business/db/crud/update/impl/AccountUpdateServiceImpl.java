package jp.co.ha.business.db.crud.update.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.business.db.mapper.AccountMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント情報更新サービス実装クラス<br>
 *
 */
@Service
public class AccountUpdateServiceImpl implements AccountUpdateService, MyBatisDao {

//	/** アカウントDao */
//	@Autowired
//	private AccountDao accountDao;
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void update(Account account) throws BaseException {
//		accountDao.update(account);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Account account) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			mapper.updateByPrimaryKey(account);
			session.commit();
		}
	}

}
