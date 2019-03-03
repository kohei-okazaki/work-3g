package jp.co.ha.business.db.crud.read.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.HealthInfoExample.Criteria;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報検索サービスインターフェース実装クラス
 *
 */
public class HealthInfoSearchServiceImpl implements HealthInfoSearchService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserId(String userId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			Criteria criteria = example.createCriteria();
			// ユーザID
			criteria.andUserIdEqualTo(userId);
			return mapper.selectByExample(example);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findByHealthInfoId(Integer healthInfoId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			return mapper.selectByPrimaryKey(healthInfoId);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findLastByUserId(String userId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			example.setOrderByClause("HEALTH_INFO_REG_DATE");
			Criteria criteria = example.createCriteria();
			// ユーザID
			criteria.andUserIdEqualTo(userId);
			return CollectionUtil.getLast(mapper.selectByExample(example));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserIdBetweenRegDate(String userId, Date fromHealthInfoRegDate, Date toHealthInfoRegDate) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			Criteria criteria = example.createCriteria();
			// ユーザID
			criteria.andUserIdEqualTo(userId);
			// 登録日時
			criteria.andRegDateBetween(fromHealthInfoRegDate, toHealthInfoRegDate);
			return mapper.selectByExample(example);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByHealthInfoIdAndUserId(Integer healthInfoId, String userId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			Criteria criteria = example.createCriteria();
			// 健康情報ID
			criteria.andHealthInfoIdEqualTo(healthInfoId);
			// ユーザID
			criteria.andUserIdEqualTo(userId);
			return mapper.selectByExample(example);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSelectCountByUserId(String userId) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			Criteria criteria = example.createCriteria();
			// ユーザID
			criteria.andUserIdEqualTo(userId);
			return (int) mapper.countByExample(example);
		}
	}

}
