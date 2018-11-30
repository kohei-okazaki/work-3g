package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.annotation.Insert;
import jp.co.ha.business.db.crud.create.AccountCreateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.mapper.AccountMapper;

/**
 * アカウント情報作成サービスインターフェース実装クラス<br>
 *
 */
public class AccountCreateServiceImpl implements AccountCreateService {

	/**
	 * {@inheritDoc}
	 */
	@Insert
	@Override
	public void create(Account entity) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}

}
