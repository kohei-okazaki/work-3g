package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定更新サービス実装クラス
 *
 */
public class HealthInfoFileSettingUpdateServiceImpl implements HealthInfoFileSettingUpdateService {

	@Autowired
	private HealthInfoFileSettingMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Update
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(HealthInfoFileSetting entity) throws BaseException {
		mapper.updateByPrimaryKey(entity);
	}
}
