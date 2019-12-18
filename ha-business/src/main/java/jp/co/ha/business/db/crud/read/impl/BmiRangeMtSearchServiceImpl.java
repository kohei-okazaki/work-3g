package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.BmiRangeMtSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.BmiRangeMt;
import jp.co.ha.db.entity.BmiRangeMtExample;
import jp.co.ha.db.mapper.BmiRangeMtMapper;

/**
 * BMI範囲マスタ検索サービスインターフェース実装クラス
 *
 * @since 1.0
 */
@Service
@CacheConfig(cacheNames = "bmiRangeMt")
public class BmiRangeMtSearchServiceImpl implements BmiRangeMtSearchService {

	/** BmiRangeMtMapper */
	@Autowired
	private BmiRangeMtMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Select
	@Override
	@Transactional(readOnly = true)
	@Cacheable(key = "'bmiRangeMt/' + #customerNo")
	public BmiRangeMt findByBmiRangeId(Integer bmiRangeId) {
		return mapper.selectByPrimaryKey(bmiRangeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Select
	@Override
	@Transactional(readOnly = true)
	@Cacheable(key = "'bmiRangeMt/' + #customerNo")
	public List<BmiRangeMt> findAll() {
		return mapper.selectByExample(new BmiRangeMtExample());
	}

}
