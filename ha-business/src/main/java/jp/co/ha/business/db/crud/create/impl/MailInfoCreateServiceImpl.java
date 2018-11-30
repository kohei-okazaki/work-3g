package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.annotation.Insert;
import jp.co.ha.business.db.crud.create.MailInfoCreateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;
import jp.co.ha.db.mapper.MailInfoMapper;

/**
 * メール情報作成サービスインターフェース実装クラス<br>
 *
 */
public class MailInfoCreateServiceImpl implements MailInfoCreateService {

	/**
	 * {@inheritDoc}
	 */
	@Insert
	@Override
	public void create(MailInfo entity) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			MailInfoMapper mapper = session.getMapper(MailInfoMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}

}
