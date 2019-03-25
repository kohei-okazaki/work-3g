package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.db.crud.read.BmiRangeMtSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.BmiRangeMt;
import jp.co.ha.db.entity.BmiRangeMtExample;
import jp.co.ha.db.mapper.BmiRangeMtMapper;

/**
 * BMI範囲マスタ検索サービスインターフェース実装クラス
 *
 */
public class BmiRangeMtSearchServiceImpl implements BmiRangeMtSearchService {

	@Autowired
	private BmiRangeMtMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BmiRangeMt findByBmiRangeId(Integer bmiRangeId) throws BaseException {
		return mapper.selectByPrimaryKey(bmiRangeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BmiRangeMt> findAll() throws BaseException {
		return mapper.selectByExample(new BmiRangeMtExample());
	}

}
