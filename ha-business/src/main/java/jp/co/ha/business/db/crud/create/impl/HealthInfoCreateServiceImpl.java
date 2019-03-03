package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報作成サービスインターフェース実装クラス
 *
 */
public class HealthInfoCreateServiceImpl implements HealthInfoCreateService {

	@Autowired
	private HealthInfoMapper mapper;

	@Insert
	@Override
	@Transactional
	public void create(HealthInfo entity) throws BaseException {
		mapper.insert(entity);
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	@Insert
//	@Override
//	@Transactional
//	public void create(HealthInfo entity) throws BaseException {
//		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
//			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
//			mapper.insert(entity);
//			session.commit();
//		}
//	}
}
