package jp.co.ha.business.db.crud.read.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定検索サービスインターフェース実装クラス
 * 
 * @since 1.0
 */
@Service
public class HealthInfoFileSettingSearchServiceImpl implements HealthInfoFileSettingSearchService {

	/** HealthInfoFileSettingMapper */
	@Autowired
	private HealthInfoFileSettingMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Select
	@Override
	@Transactional(readOnly = true)
	public HealthInfoFileSetting findByUserId(String userId) throws BaseException {
		return mapper.selectByPrimaryKey(userId);
	}
}
