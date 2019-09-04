package jp.co.ha.business.db.crud.delete.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.delete.HealthInfoDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報削除サービスインターフェース実装クラス
 *
 */
@Service
public class HealthInfoDeleteServiceImpl implements HealthInfoDeleteService {

	/** HealthInfoMapper */
	@Autowired
	private HealthInfoMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Delete
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByUserId(Integer healthInfoId) throws BaseException {
		mapper.deleteByPrimaryKey(healthInfoId);
	}
}
