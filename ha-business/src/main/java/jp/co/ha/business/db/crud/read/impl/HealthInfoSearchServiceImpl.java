package jp.co.ha.business.db.crud.read.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.HealthInfoExample.Criteria;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報検索サービスインターフェース実装クラス
 *
 * @since 1.0
 */
@Service
public class HealthInfoSearchServiceImpl implements HealthInfoSearchService {

	/** HealthInfoMapper */
	@Autowired
	private HealthInfoMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Select
	@Override
	@Transactional(readOnly = true)
	public List<HealthInfo> findByUserIdBetweenHealthInfoRegDate(String userId, Date fromHealthInfoRegDate,
			Date toHealthInfoRegDate) {

		HealthInfoExample example = new HealthInfoExample();
		Criteria criteria = example.createCriteria();
		// ユーザID
		criteria.andUserIdEqualTo(userId);
		// 健康情報登録日時
		criteria.andHealthInfoRegDateBetween(fromHealthInfoRegDate, toHealthInfoRegDate);

		// 健康情報登録日時の昇順でソート
		example.setOrderByClause("HEALTH_INFO_REG_DATE");
		return mapper.selectByExample(example);
	}

	/**
	 * {@inheritDoc}
	 */
	@Select
	@Override
	@Transactional(readOnly = true)
	public List<HealthInfo> findByHealthInfoIdAndUserId(Integer healthInfoId, String userId) {

		HealthInfoExample example = new HealthInfoExample();
		Criteria criteria = example.createCriteria();
		// 健康情報ID
		criteria.andHealthInfoIdEqualTo(healthInfoId);
		// ユーザID
		criteria.andUserIdEqualTo(userId);

		return mapper.selectByExample(example);
	}

	/**
	 * {@inheritDoc}
	 */
	@Select
	@Override
	@Transactional(readOnly = true)
	public int getSelectCountByUserId(String userId) {

		HealthInfoExample example = new HealthInfoExample();
		Criteria criteria = example.createCriteria();
		// ユーザID
		criteria.andUserIdEqualTo(userId);

		return (int) mapper.countByExample(example);
	}

	/**
	 * {@inheritDoc}
	 */
	@Select
	@Override
	@Transactional(readOnly = true)
	public List<HealthInfo> findByUserId(String userId, SelectOption selectOption) {

		HealthInfoExample example = new HealthInfoExample();
		Criteria criteria = example.createCriteria();
		// ユーザID
		criteria.andUserIdEqualTo(userId);
		// ソート処理
		example.setOrderByClause(selectOption.toOrderBy());
		// 検索上限数
		example.setLimit(selectOption.getLimit());

		return mapper.selectByExample(example);
	}

}
