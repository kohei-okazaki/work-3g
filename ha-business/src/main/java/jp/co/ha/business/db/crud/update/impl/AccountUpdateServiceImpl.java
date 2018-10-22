package jp.co.ha.business.db.crud.update.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.mapper.AccountMapper;

/**
 * アカウント情報更新サービス実装クラス<br>
 *
 */
public class AccountUpdateServiceImpl implements AccountUpdateService, MyBatisDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Account entity) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			mapper.updateByPrimaryKey(entity);
			session.commit();
		}
	}

}
