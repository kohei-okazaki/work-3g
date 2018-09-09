package jp.co.ha.business.db.crud.update.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.crud.update.MailInfoUpdateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.MailInfo;
import jp.co.ha.business.db.mapper.MailInfoMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * メール情報更新サービス実装クラス<br>
 *
 */
public class MailInfoUpdateServiceImpl implements MailInfoUpdateService, MyBatisDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(MailInfo mailInfo) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			MailInfoMapper mapper = session.getMapper(MailInfoMapper.class);
			mapper.updateByPrimaryKey(mailInfo);
			session.commit();
		}
	}

}
