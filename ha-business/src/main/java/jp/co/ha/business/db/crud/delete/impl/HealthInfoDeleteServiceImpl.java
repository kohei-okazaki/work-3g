package jp.co.ha.business.db.crud.delete.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.delete.HealthInfoDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報削除サービスインターフェース実装クラス
 *
 */
public class HealthInfoDeleteServiceImpl implements HealthInfoDeleteService {

	@Autowired
	private HealthInfoMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Delete
	@Override
	@Transactional
	public void deleteByUserId(Integer healthInfoId) throws BaseException {
		mapper.deleteByPrimaryKey(healthInfoId);
	}
}
