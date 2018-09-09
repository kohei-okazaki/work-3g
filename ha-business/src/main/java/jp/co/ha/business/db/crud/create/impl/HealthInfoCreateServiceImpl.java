package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.business.db.mapper.HealthInfoMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報作成サービスインターフェース実装クラス<br>
 *
 */
@Service
public class HealthInfoCreateServiceImpl implements HealthInfoCreateService, MyBatisDao {

//	/** 健康情報Dao */
//	@Autowired
//	private HealthInfoDao healthInfoDao;
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void create(HealthInfo entity) throws BaseException {
//		healthInfoDao.create(entity);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(HealthInfo entity) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}
}
