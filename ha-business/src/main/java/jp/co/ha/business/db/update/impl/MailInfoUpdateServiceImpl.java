package jp.co.ha.business.db.update.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.MailInfo;
import jp.co.ha.business.db.mapper.MailInfoMapper;
import jp.co.ha.business.db.update.MailInfoUpdateService;
import jp.co.ha.common.exception.DataBaseException;

/**
 * メール情報更新サービス実装クラス<br>
 *
 */
@Service
public class MailInfoUpdateServiceImpl implements MailInfoUpdateService, MyBatisDao {

//	/** メール情報Dao */
//	@Autowired
//	private MailInfoDao mailInfoDao;
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void update(MailInfo mailInfo) throws DataBaseException {
//		mailInfoDao.update(mailInfo);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(MailInfo mailInfo) throws DataBaseException {
		try (SqlSession session = getSqlSession()) {
			MailInfoMapper mapper = session.getMapper(MailInfoMapper.class);
			mapper.updateByPrimaryKey(mailInfo);
			session.commit();
		}
	}

}
