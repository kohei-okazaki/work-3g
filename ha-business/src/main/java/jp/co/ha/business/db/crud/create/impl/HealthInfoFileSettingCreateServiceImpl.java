package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.HealthInfoFileSettingCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定作成サービスインターフェース実装クラス
 *
 */
@Service
public class HealthInfoFileSettingCreateServiceImpl implements HealthInfoFileSettingCreateService {

	/** HealthInfoFileSettingMapper */
	@Autowired
	private HealthInfoFileSettingMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Insert
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void create(HealthInfoFileSetting entity) throws BaseException {
		mapper.insert(entity);
	}
}
