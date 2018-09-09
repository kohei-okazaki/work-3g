package jp.co.ha.business.db.create.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.create.HealthInfoFileSettingCreateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.business.db.mapper.HealthInfoFileSettingMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報ファイル設定作成サービスインターフェース実装クラス<br>
 *
 */
@Service
public class HealthInfoFileSettingCreateServiceImpl implements HealthInfoFileSettingCreateService, MyBatisDao {

//	/** 健康情報ファイル設定Dao */
//	@Autowired
//	private HealthInfoFileSettingDao healthInfoFileSettingDao;
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void create(HealthInfoFileSetting entity) throws BaseException {
//		healthInfoFileSettingDao.create(entity);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(HealthInfoFileSetting entity) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			HealthInfoFileSettingMapper mapper = session.getMapper(HealthInfoFileSettingMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}

}
