package jp.co.ha.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.service.HealthInfoSearchService;

/**
 * 健康情報検索サービス実装クラス<br>
 *
 */
@Service
public class HealthInfoSearchServiceImpl implements HealthInfoSearchService {

	/** 健康情報Dao */
	@Autowired
	private HealthInfoDao healthInfoDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findHealthInfoByUserId(String userId) {
		return this.healthInfoDao.getHealthInfoByUserId(userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findHealthInfoByDataId(String dataId) {
		return this.healthInfoDao.getHealthInfoByDataId(dataId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findLastHealthInfoByUserId(String userId) {
		return healthInfoDao.getLastHealthInfoByUserId(userId);
	}

}
