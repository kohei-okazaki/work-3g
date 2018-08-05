package jp.co.ha.business.db.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.create.HealthInfoCreateService;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報作成サービスインターフェース実装クラス<br>
 *
 */
@Service
public class HealthInfoCreateServiceImpl implements HealthInfoCreateService {

	/** 健康情報Dao */
	@Autowired
	private HealthInfoDao healthInfoDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(HealthInfo entity) throws BaseException {
		healthInfoDao.create(entity);
	}

}
