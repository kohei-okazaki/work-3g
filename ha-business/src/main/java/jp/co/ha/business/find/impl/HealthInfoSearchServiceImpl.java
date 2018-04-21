package jp.co.ha.business.find.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;

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
	public List<HealthInfo> findByUserId(String userId) {
		return this.healthInfoDao.getHealthInfoByUserId(userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findByDataId(String dataId) {
		return this.healthInfoDao.getHealthInfoByDataId(dataId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findLastByUserId(String userId) {
		return healthInfoDao.getLastHealthInfoByUserId(userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserIdAndRegDate(String userId, Date regDate) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
