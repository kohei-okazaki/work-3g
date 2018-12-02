package jp.co.ha.business.db.crud.update.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.annotation.Update;
import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.mapper.AccountMapper;

/**
 * アカウント情報更新サービス実装クラス<br>
 *
 */
public class AccountUpdateServiceImpl implements AccountUpdateService {

	/**
	 * {@inheritDoc}
	 */
	@Update
	@Override
	public void update(Account entity) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			mapper.updateByPrimaryKey(entity);
			session.commit();
		}
	}

}
