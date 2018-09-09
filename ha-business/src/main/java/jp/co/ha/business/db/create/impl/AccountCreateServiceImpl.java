package jp.co.ha.business.db.create.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.create.AccountCreateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.business.db.mapper.AccountMapper;
import jp.co.ha.common.exception.DataBaseException;

/**
 * アカウント情報作成サービスインターフェース実装クラス<br>
 *
 */
@Service
public class AccountCreateServiceImpl implements AccountCreateService, MyBatisDao {

//	/** アカウント情報Dao */
//	@Autowired
//	private AccountDao accountDao;
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void create(Account entity) throws DataBaseException {
//		accountDao.create(entity);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(Account entity) throws DataBaseException {
		try (SqlSession session = getSqlSession()) {
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}

}
