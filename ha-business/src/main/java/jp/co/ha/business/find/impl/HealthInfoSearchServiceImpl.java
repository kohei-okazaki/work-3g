package jp.co.ha.business.find.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.util.BeanUtil;
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

		if (BeanUtil.isNull(entityList) || entityList.isEmpty()) {
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
		if (BeanUtil.isNull(regDate)) {
			// 検索条件がない場合
			return healthInfoList;
		}
		List<HealthInfo> resultList = healthInfoList.stream()
						.filter(entity -> regDate.compareTo(DateUtil.toStartDate(entity.getRegDate())) == 0)
						.collect(Collectors.toList());
		return resultList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserIdBetweenRegDate(String userId, Date fromRegDate, Date toRegDate) {

		List<HealthInfo> healthInfoList = healthInfoDao.findByUserId(userId);
		if (BeanUtil.isNull(fromRegDate) || BeanUtil.isNull(toRegDate)) {
			// 検索条件がない場合
			return healthInfoList;
		}
		// fromRegDate < entityRegDate < toRegDateの範囲内である健康情報の場合、追加
		List<HealthInfo> resultList = healthInfoList.stream()
									.filter(entity -> DateUtil.isBetWeenDate(fromRegDate, entity.getRegDate(), toRegDate))
									.collect(Collectors.toList());
		return resultList;
	}

}
