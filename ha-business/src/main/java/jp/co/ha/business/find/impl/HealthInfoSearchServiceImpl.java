package jp.co.ha.business.find.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.util.DateUtil;

/**
 * 健康情報検索サービスインターフェース実装クラス<br>
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
		return healthInfoDao.findByUserId(userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findByDataId(String dataId) {
		return healthInfoDao.findByDataId(dataId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findLastByUserId(String userId) {

		List<HealthInfo> entityList = this.findByUserId(userId);

		if (Objects.isNull(entityList) || entityList.isEmpty()) {
			// 登録がされてなかった場合
			return null;
		}
		return entityList.get(entityList.size() - 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserIdAndRegDate(String userId, Date regDate) {

		List<HealthInfo> healthInfoList = healthInfoDao.findByUserId(userId);

		if (Objects.isNull(regDate)) {
			// 検索条件がない場合
			return healthInfoList;
		}
		List<HealthInfo> resultList = new ArrayList<HealthInfo>();

		healthInfoList.stream().forEach(healthInfo -> {
			Date entityDate = healthInfo.getRegDate();
			entityDate = DateUtil.toStartDate(entityDate);
			if (regDate.compareTo(entityDate) == 0) {
				// 同じ日付の場合
				resultList.add(healthInfo);
			}
		});

		return resultList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserIdBetweenRegDate(String userId, Date fromRegDate, Date toRegDate) {

		List<HealthInfo> healthInfoList = healthInfoDao.findByUserId(userId);

		if (Objects.isNull(fromRegDate) || Objects.isNull(toRegDate)) {
			// 検索条件がない場合
			return healthInfoList;
		}
		List<HealthInfo> resultList = new ArrayList<HealthInfo>();

		healthInfoList.stream().forEach(healthInfo -> {
			Date entityRegDate = healthInfo.getRegDate();
			if (fromRegDate.after(entityRegDate) || toRegDate.before(entityRegDate)) {
				// fromRegDate < entityRegDate < toRegDateの範囲内である健康情報の場合、追加
				resultList.add(healthInfo);
			}
		});

		return resultList;
	}

}
