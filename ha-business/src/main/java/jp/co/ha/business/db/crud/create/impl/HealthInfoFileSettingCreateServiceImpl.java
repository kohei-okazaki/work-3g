package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.create.HealthInfoFileSettingCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定作成サービスインターフェース実装クラス
 *
 */
public class HealthInfoFileSettingCreateServiceImpl implements HealthInfoFileSettingCreateService {

	/**
	 * {@inheritDoc}
	 */
	@Insert
	@Override
	@Transactional
	public void create(HealthInfoFileSetting entity) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoFileSettingMapper mapper = session.getMapper(HealthInfoFileSettingMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}

}
