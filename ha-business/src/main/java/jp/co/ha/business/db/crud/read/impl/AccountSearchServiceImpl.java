package jp.co.ha.business.db.crud.read.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.mapper.AccountMapper;

/**
 * アカウント情報検索サービスインターフェース実装クラス<br>
 *
 */
public class AccountSearchServiceImpl implements AccountSearchService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account findByUserId(String userId) throws BaseException {

		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			return mapper.selectByPrimaryKey(userId);
		}
	}
}
