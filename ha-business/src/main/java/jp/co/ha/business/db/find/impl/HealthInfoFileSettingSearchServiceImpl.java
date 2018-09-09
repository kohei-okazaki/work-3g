package jp.co.ha.business.db.find.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.business.db.find.HealthInfoFileSettingSearchService;
import jp.co.ha.business.db.mapper.HealthInfoFileSettingMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報ファイル設定検索サービスインターフェース実装クラス<br>
 *
 */
@Service
public class HealthInfoFileSettingSearchServiceImpl implements HealthInfoFileSettingSearchService, MyBatisDao {

//	/** 健康情報ファイル設定Dao */
//	@Autowired
//	private HealthInfoFileSettingDao healthInfoFileSettingDao;

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public HealthInfoFileSetting findByUserId(String userId) throws BaseException {
//		return healthInfoFileSettingDao.selectByUserId(userId);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoFileSetting findByUserId(String userId) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			HealthInfoFileSettingMapper mapper = session.getMapper(HealthInfoFileSettingMapper.class);
			return mapper.selectByPrimaryKey(userId);
		}
	}
}
