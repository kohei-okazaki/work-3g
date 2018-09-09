package jp.co.ha.business.db.crud.create.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.business.db.mapper.HealthInfoMapper;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報作成サービスインターフェース実装クラス<br>
 *
 */
public class HealthInfoCreateServiceImpl implements HealthInfoCreateService, MyBatisDao {

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
