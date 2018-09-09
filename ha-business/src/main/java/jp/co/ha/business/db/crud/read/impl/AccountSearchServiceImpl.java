package jp.co.ha.business.db.crud.read.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.business.db.mapper.AccountMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント情報検索サービスインターフェース実装クラス<br>
 *
 */
@Service
public class AccountSearchServiceImpl implements AccountSearchService, MyBatisDao {

//	/** アカウント情報Dao */
//	@Autowired
//	private AccountDao accountDao;

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public Account findByUserId(String userId) throws BaseException {
//		return accountDao.selectByUserId(userId);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account findByUserId(String userId) throws BaseException {

		try (SqlSession session = getSqlSession()) {
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			return mapper.selectByPrimaryKey(userId);
		}
	}
}
