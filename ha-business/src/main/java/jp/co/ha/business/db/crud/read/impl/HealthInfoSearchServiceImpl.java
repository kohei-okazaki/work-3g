package jp.co.ha.business.db.crud.read.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import jp.co.ha.business.db.SqlSessionFactory;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.HealthInfoExample.Criteria;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報検索サービスインターフェース実装クラス<br>
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
			Criteria criteria = example.createCriteria();
			// ユーザID
			criteria.andUserIdEqualTo(userId);
			List<HealthInfo> list = mapper.selectByExample(example);
			if (BeanUtil.isNull(list) || list.isEmpty()) {
				return null;
			}
			return list.get(list.size() - 1);
		}
	}

	/**
	 * {@inheritDoc}

	 */
	@Override
	public List<HealthInfo> findByUserIdAndRegDate(String userId, Date regDate) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			Criteria criteria = example.createCriteria();
			// ユーザID
			criteria.andUserIdEqualTo(userId);
			// 登録日時
			criteria.andRegDateEqualTo(regDate);
			return mapper.selectByExample(example);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserIdBetweenRegDate(String userId, Date fromRegDate, Date toRegDate) throws BaseException {
		try (SqlSession session = SqlSessionFactory.getInstance().getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			Criteria criteria = example.createCriteria();
			// ユーザID
			criteria.andUserIdEqualTo(userId);
			// 登録日時
			criteria.andRegDateBetween(fromRegDate, toRegDate);
			return mapper.selectByExample(example);
		}
	}

}
