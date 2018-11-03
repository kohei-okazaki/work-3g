package jp.co.ha.business.db.crud.update.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定更新サービス実装クラス
 *
 */
public class HealthInfoFileSettingUpdateServiceImpl implements HealthInfoFileSettingUpdateService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(HealthInfoFileSetting entity) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoFileSettingMapper mapper = session.getMapper(HealthInfoFileSettingMapper.class);
			mapper.updateByPrimaryKey(entity);
			session.commit();
		}
	}

}
