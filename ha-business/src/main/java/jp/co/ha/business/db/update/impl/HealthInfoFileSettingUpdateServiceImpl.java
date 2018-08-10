package jp.co.ha.business.db.update.impl;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.db.dao.HealthInfoFileSettingDao;
import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.business.db.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.common.exception.DataBaseException;

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
	public void update(HealthInfoFileSetting entity) throws DataBaseException {
		healthInfoFileSettingDao.update(entity);
	}

}
