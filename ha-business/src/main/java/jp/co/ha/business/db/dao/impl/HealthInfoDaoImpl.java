package jp.co.ha.business.db.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.ha.business.db.dao.HealthInfoDao;
import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.common.dao.BaseDao;
import jp.co.ha.common.exception.DataBaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.DateUtil;

/**
 * 健康情報のDaoクラス
 *
 */
public class HealthInfoDaoImpl extends BaseDao implements HealthInfoDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> selectByUserId(String userId) throws DataBaseException {

		List<HealthInfo> healthInfoList = new ArrayList<HealthInfo>();
		try {
			connect();
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE USER_ID = '" + userId + "'";
			execute(sql, SqlType.SELECT);
			while (hasNext()) {
				HealthInfo healthInfo = new HealthInfo();
				healthInfo.setHealthInfoId(rs.getBigDecimal(HEALTH_INFO_ID));
				healthInfo.setUserId(rs.getString(USER_ID));
				healthInfo.setHeight(rs.getBigDecimal(HEIGHT));
				healthInfo.setWeight(rs.getBigDecimal(WEIGHT));
				healthInfo.setBmi(rs.getBigDecimal(BMI));
				healthInfo.setStandardWeight(rs.getBigDecimal(STANDARD_WEIGHT));
				healthInfo.setUserStatus(rs.getString(USER_STATUS));
				healthInfo.setRegDate(rs.getTimestamp(REG_DATE));
				healthInfoList.add(healthInfo);
			}
		} catch (SQLException e) {
			LOG.error(ErrorCode.DB_ACCESS_ERROR.getErrorMessage(), e);
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, TABLE_NAME + "テーブルへのアクセスに失敗しました");
		} finally {
			close();
		}

		return healthInfoList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo selectByHealthInfoId(BigDecimal healthInfoId) throws DataBaseException {

		HealthInfo healthInfo = null;
		try {
			connect();
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE HEALTH_INFO_ID = '" + healthInfoId.toString() + "'";
			execute(sql, SqlType.SELECT);
			while (hasNext()) {
				healthInfo = new HealthInfo();
				healthInfo.setHealthInfoId(rs.getBigDecimal(HEALTH_INFO_ID));
				healthInfo.setUserId(rs.getString(USER_ID));
				healthInfo.setHeight(rs.getBigDecimal(HEIGHT));
				healthInfo.setWeight(rs.getBigDecimal(WEIGHT));
				healthInfo.setBmi(rs.getBigDecimal(BMI));
				healthInfo.setStandardWeight(rs.getBigDecimal(STANDARD_WEIGHT));
				healthInfo.setUserStatus(rs.getString(USER_STATUS));
				healthInfo.setRegDate(rs.getTimestamp(REG_DATE));
			}
		} catch (SQLException e) {
			LOG.error(ErrorCode.DB_ACCESS_ERROR.getErrorMessage(), e);
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, TABLE_NAME + "テーブルへのアクセスに失敗しました");
		} finally {
			close();
		}
		return healthInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(HealthInfo healthInfo) throws DataBaseException {
		LOG.infoRes(healthInfo);
		try {
			connect();
			String sql = "INSERT INTO " + TABLE_NAME + "("
												+ USER_ID + ", "
												+ HEIGHT + ", "
												+ WEIGHT + ", "
												+ BMI + ", "
												+ STANDARD_WEIGHT + ", "
												+ USER_STATUS + ", "
												+ REG_DATE
												+ ") VALUES ("
												+ "'" + healthInfo.getUserId() + "', "
												+ "'" + healthInfo.getHeight() + "', "
												+ "'" + healthInfo.getWeight() + "', "
												+ "'" + healthInfo.getBmi() + "', "
												+ "'" + healthInfo.getStandardWeight() + "', "
												+ "'" + healthInfo.getUserStatus() + "', "
												+ "'" + DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS) + "'"
												+ ")";
			int rs = execute(sql, SqlType.INSERT);
			System.out.println("結果" + rs);
		} finally {
			close();
		}
	}

}
