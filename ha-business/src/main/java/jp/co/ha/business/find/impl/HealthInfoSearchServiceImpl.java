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
		return healthInfoDao.selectByUserId(userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findByHealthInfoId(String healthInfoId) {
		return healthInfoDao.selectByHealthInfoId(healthInfoId);
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

		List<HealthInfo> healthInfoList = healthInfoDao.selectByUserId(userId);
		if (BeanUtil.isNull(regDate)) {
			// 検索条件がない場合
			return healthInfoList;
		}
		return healthInfoList.stream()
					.filter(entity -> DateUtil.isSameDate(regDate, DateUtil.toStartDate(entity.getRegDate())))
					.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserIdBetweenRegDate(String userId, Date fromRegDate, Date toRegDate) {

		List<HealthInfo> healthInfoList = healthInfoDao.selectByUserId(userId);
		if (BeanUtil.isNull(fromRegDate) || BeanUtil.isNull(toRegDate)) {
			// 検索条件がない場合
			return healthInfoList;
		}
		// fromRegDate < entityRegDate < toRegDateの範囲内である健康情報の場合、追加
		return healthInfoList.stream()
					.filter(entity -> DateUtil.isBetWeenDate(fromRegDate, entity.getRegDate(), toRegDate))
					.collect(Collectors.toList());
	}

}
