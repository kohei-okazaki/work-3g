package jp.co.ha.business.db.crud.read.impl;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定検索サービスインターフェース実装クラス
 *
 */
public class HealthInfoFileSettingSearchServiceImpl implements HealthInfoFileSettingSearchService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoFileSetting findByUserId(String userId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoFileSettingMapper mapper = session.getMapper(HealthInfoFileSettingMapper.class);
			return mapper.selectByPrimaryKey(userId);
		}
	}
}
