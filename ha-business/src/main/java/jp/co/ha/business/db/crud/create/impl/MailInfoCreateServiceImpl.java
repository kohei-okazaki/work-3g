package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.create.MailInfoCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;
import jp.co.ha.db.mapper.MailInfoMapper;

/**
 * メール情報作成サービスインターフェース実装クラス
 *
 */
public class MailInfoCreateServiceImpl implements MailInfoCreateService {

	/**
	 * {@inheritDoc}
	 */
	@Insert
	@Override
	@Transactional
	public void create(MailInfo entity) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			MailInfoMapper mapper = session.getMapper(MailInfoMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}

}
