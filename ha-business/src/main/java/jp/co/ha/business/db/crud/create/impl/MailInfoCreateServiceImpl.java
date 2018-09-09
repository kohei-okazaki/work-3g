package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.create.MailInfoCreateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.MailInfo;
import jp.co.ha.business.db.mapper.MailInfoMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * メール情報作成サービスインターフェース実装クラス<br>
 *
 */
@Service
public class MailInfoCreateServiceImpl implements MailInfoCreateService, MyBatisDao {

//	/** メール情報Dao */
//	@Autowired
//	private MailInfoDao mailInfoDao;
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void create(MailInfo entity) throws BaseException {
//		mailInfoDao.create(entity);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(MailInfo entity) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			MailInfoMapper mapper = session.getMapper(MailInfoMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}

}
