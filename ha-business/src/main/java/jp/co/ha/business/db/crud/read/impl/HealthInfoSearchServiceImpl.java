package jp.co.ha.business.db.crud.read.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.dao.MyBatisDao;
import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.business.db.entity.HealthInfoExample;
import jp.co.ha.business.db.mapper.HealthInfoMapper;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;

/**
 * 健康情報検索サービスインターフェース実装クラス<br>
 *
 */
@Service
public class HealthInfoSearchServiceImpl implements HealthInfoSearchService, MyBatisDao {

//	/** 健康情報Dao */
//	@Autowired
//	private HealthInfoDao healthInfoDao;

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public List<HealthInfo> findByUserId(String userId) throws BaseException {
//		return healthInfoDao.selectByUserId(userId);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserId(String userId) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			example.createCriteria().andUserIdEqualTo(userId);
			return mapper.selectByExample(example);
		}
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public HealthInfo findByHealthInfoId(Integer healthInfoId) throws BaseException {
//		return healthInfoDao.selectByHealthInfoId(healthInfoId);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findByHealthInfoId(Integer healthInfoId) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			return mapper.selectByPrimaryKey(healthInfoId);
		}
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public HealthInfo findLastByUserId(String userId) throws BaseException {
//		List<HealthInfo> entityList = this.findByUserId(userId);
//		if (BeanUtil.isNull(entityList) || entityList.isEmpty()) {
//			// 登録がされてなかった場合
//			return null;
//		}
//		return entityList.get(entityList.size() - 1);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo findLastByUserId(String userId) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			// ユーザIDの設定
			example.createCriteria().andUserIdEqualTo(userId);
			List<HealthInfo> list = mapper.selectByExample(example);
			if (BeanUtil.isNull(list) || list.isEmpty()) {
				return null;
			}
			return list.get(list.size() - 1);
		}
	}

//	/**
//	 * {@inheritDoc}
//
//	 */
//	@Override
//	public List<HealthInfo> findByUserIdAndRegDate(String userId, Date regDate) throws BaseException {
//
//		List<HealthInfo> healthInfoList = healthInfoDao.selectByUserId(userId);
//		if (BeanUtil.isNull(regDate)) {
//			// 検索条件がない場合
//			return healthInfoList;
//		}
//		return healthInfoList.stream()
//					.filter(entity -> DateUtil.isSameDate(regDate, DateUtil.toStartDate(entity.getRegDate())))
//					.collect(Collectors.toList());
//	}

	/**
	 * {@inheritDoc}

	 */
	@Override
	public List<HealthInfo> findByUserIdAndRegDate(String userId, Date regDate) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			example.createCriteria().andUserIdEqualTo(userId).andRegDateEqualTo(regDate);
			return mapper.selectByExample(example);
		}
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public List<HealthInfo> findByUserIdBetweenRegDate(String userId, Date fromRegDate, Date toRegDate) throws BaseException {
//
//		List<HealthInfo> healthInfoList = healthInfoDao.selectByUserId(userId);
//		if (BeanUtil.isNull(fromRegDate) || BeanUtil.isNull(toRegDate)) {
//			// 検索条件がない場合
//			return healthInfoList;
//		}
//		// fromRegDate < entityRegDate < toRegDateの範囲内である健康情報の場合、追加
//		return healthInfoList.stream()
//					.filter(entity -> DateUtil.isBetWeenDate(fromRegDate, entity.getRegDate(), toRegDate))
//					.collect(Collectors.toList());
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> findByUserIdBetweenRegDate(String userId, Date fromRegDate, Date toRegDate) throws BaseException {
		try (SqlSession session = getSqlSession()) {
			HealthInfoMapper mapper = session.getMapper(HealthInfoMapper.class);
			HealthInfoExample example = new HealthInfoExample();
			example.createCriteria().andUserIdEqualTo(userId).andRegDateBetween(fromRegDate, toRegDate);
			return mapper.selectByExample(example);
		}
	}

}
