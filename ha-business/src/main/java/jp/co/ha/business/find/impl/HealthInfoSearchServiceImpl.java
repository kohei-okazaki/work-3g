package jp.co.ha.business.find.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;

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
		return healthInfoDao.getHealthInfoByUserId(userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findByDataId(String dataId) {
		return healthInfoDao.getHealthInfoByDataId(dataId);
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
	public List<HealthInfo> findByUserIdAndRegDate(String userId, String year, String month, String day) {

		List<HealthInfo> healthInfoList = healthInfoDao.getHealthInfoByUserId(userId);

		if (StringUtil.isEmpty(year) && StringUtil.isEmpty(month) && StringUtil.isEmpty(day)) {
			// 検索条件がない場合
			return healthInfoList;
		}
		List<HealthInfo> resultList = new ArrayList<HealthInfo>();

		healthInfoList.stream().forEach(healthInfo -> {
			String strRegDate = DateUtil.toString(healthInfo.getRegDate(), DateFormatDefine.YYYYMMDD);
			String strRegYear = strRegDate.substring(0, 4);
			String strRegMonth = strRegDate.substring(5, 7);
			String strRegDay = strRegDate.substring(8, 10);

			if (strRegYear.equals(year) && strRegMonth.equals(month) && strRegDay.equals(day)) {
				resultList.add(healthInfo);
			}
		});

		return resultList;
	}

}
