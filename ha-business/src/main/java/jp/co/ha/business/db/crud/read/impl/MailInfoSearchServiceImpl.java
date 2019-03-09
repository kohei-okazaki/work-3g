package jp.co.ha.business.db.crud.read.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.read.MailInfoSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;
import jp.co.ha.db.mapper.MailInfoMapper;

/**
 * メール情報検索サービスインターフェース実装クラス
 *
 */
public class MailInfoSearchServiceImpl implements MailInfoSearchService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public MailInfo findByUserId(String userId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			MailInfoMapper mapper = session.getMapper(MailInfoMapper.class);
			return mapper.selectByPrimaryKey(userId);
		}
	}
}
