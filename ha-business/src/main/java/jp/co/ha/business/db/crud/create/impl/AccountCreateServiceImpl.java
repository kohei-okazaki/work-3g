package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.crud.create.AccountCreateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.business.db.mapper.AccountMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント情報作成サービスインターフェース実装クラス<br>
 *
 */
public class AccountCreateServiceImpl implements AccountCreateService, MyBatisDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(Account entity) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}

}
