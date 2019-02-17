package jp.co.ha.business.db.crud.delete.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.delete.AccountDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.mapper.AccountMapper;

/**
 *  アカウント情報削除サービスインターフェース実装クラス
 *
 */
public class AccountDeleteServiceImpl implements AccountDeleteService {

	/**
	 * {@inheritDoc}
	 */
	@Delete
	@Override
	public void deleteByUserId(String userId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			mapper.deleteByPrimaryKey(userId);
			session.commit();
		}
	}

}
