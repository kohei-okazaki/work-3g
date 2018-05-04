package jp.co.ha.business.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.create.HealthInfoCreateService;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;

@Service
public class HealthInfoCreateServiceImpl implements HealthInfoCreateService {

	/** 健康情報Dao */
	@Autowired
	private HealthInfoDao healthInfoDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(HealthInfo healthInfo) {
		healthInfoDao.create(healthInfo);
	}

}
