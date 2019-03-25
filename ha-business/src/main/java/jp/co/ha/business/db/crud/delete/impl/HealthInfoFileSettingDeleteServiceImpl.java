package jp.co.ha.business.db.crud.delete.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.delete.HealthInfoFileSettingDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定削除サービスインターフェース実装クラス
 *
 */
public class HealthInfoFileSettingDeleteServiceImpl implements HealthInfoFileSettingDeleteService {

	@Autowired
	private HealthInfoFileSettingMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Delete
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByUserId(String userId) throws BaseException {
		mapper.deleteByPrimaryKey(userId);
	}
}
