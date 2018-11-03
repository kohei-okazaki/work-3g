package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報作成サービスインターフェース実装クラス<br>
 *
 */
public class HealthInfoCreateServiceImpl implements HealthInfoCreateService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(HealthInfo entity) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			mapper.insert(entity);
			session.commit();
		}
	}
}
