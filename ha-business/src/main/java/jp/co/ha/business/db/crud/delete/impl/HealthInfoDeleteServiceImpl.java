package jp.co.ha.business.db.crud.delete.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.delete.HealthInfoDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報削除サービスインターフェース実装クラス
 *
 */
public class HealthInfoDeleteServiceImpl implements HealthInfoDeleteService {

	/**
	 * {@inheritDoc}
	 */
	@Delete
	@Override
	@Transactional
	public void deleteByUserId(Integer healthInfoId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			mapper.deleteByPrimaryKey(healthInfoId);
			session.commit();
		}
	}

}
