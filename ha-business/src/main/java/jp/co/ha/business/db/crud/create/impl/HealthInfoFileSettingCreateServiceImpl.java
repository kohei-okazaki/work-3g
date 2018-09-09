package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.crud.create.HealthInfoFileSettingCreateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.business.db.mapper.HealthInfoFileSettingMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報ファイル設定作成サービスインターフェース実装クラス<br>
 *
 */
public class HealthInfoFileSettingCreateServiceImpl implements HealthInfoFileSettingCreateService, MyBatisDao {

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
