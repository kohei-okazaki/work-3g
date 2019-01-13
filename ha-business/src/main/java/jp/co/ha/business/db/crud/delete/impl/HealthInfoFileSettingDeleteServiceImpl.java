package jp.co.ha.business.db.crud.delete.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.delete.HealthInfoFileSettingDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定削除サービス実装クラス
 *
 */
public class HealthInfoFileSettingDeleteServiceImpl implements HealthInfoFileSettingDeleteService {

	/**
	 * {@inheritDoc}
	 */
	@Delete
	@Override
	public void deleteByUserId(String userId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoFileSettingMapper mapper = session.getMapper(HealthInfoFileSettingMapper.class);
			mapper.deleteByPrimaryKey(userId);
			session.commit();
		}
	}
}
