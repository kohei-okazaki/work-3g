package jp.co.ha.business.db.crud.update.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.update.MailInfoUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;
import jp.co.ha.db.mapper.MailInfoMapper;

/**
 * メール情報更新サービス実装クラス<br>
 *
 */
public class MailInfoUpdateServiceImpl implements MailInfoUpdateService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(MailInfo entity) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			MailInfoMapper mapper = session.getMapper(MailInfoMapper.class);
			mapper.updateByPrimaryKey(entity);
			session.commit();
		}
	}

}
