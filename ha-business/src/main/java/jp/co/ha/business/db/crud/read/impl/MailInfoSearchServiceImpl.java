package jp.co.ha.business.db.crud.read.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.MailInfoSearchService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.MailInfo;
import jp.co.ha.business.db.mapper.MailInfoMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * メール情報検索サービスインターフェース実装クラス<br>
 *
 */
@Service
public class MailInfoSearchServiceImpl implements MailInfoSearchService, MyBatisDao {

//	/** メール情報Dao */
//	@Autowired
//	private MailInfoDao mailInfoDao;
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public MailInfo findByUserId(String userId) throws BaseException {
//		return mailInfoDao.selectByUserId(userId);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailInfo findByUserId(String userId) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			MailInfoMapper mapper = session.getMapper(MailInfoMapper.class);
			return mapper.selectByPrimaryKey(userId);
		}
	}
}
