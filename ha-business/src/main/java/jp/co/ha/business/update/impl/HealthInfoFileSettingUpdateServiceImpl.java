package jp.co.ha.business.update.impl;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.common.dao.HealthInfoFileSettingDao;
import jp.co.ha.common.entity.HealthInfoFileSetting;

/**
 * 健康情報ファイル設定更新サービス実装クラス
 *
 */
public class HealthInfoFileSettingUpdateServiceImpl implements HealthInfoFileSettingUpdateService {

	/** 健康情報ファイル設定Dao */
	@Autowired
	private HealthInfoFileSettingDao healthInfoFileSettingDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(HealthInfoFileSetting entity) {
		healthInfoFileSettingDao.update(entity);
	}

}
