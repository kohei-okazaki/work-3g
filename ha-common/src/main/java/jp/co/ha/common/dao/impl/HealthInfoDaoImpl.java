package jp.co.ha.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.dao.BaseDaoImpl;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.DataBaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.AppLoggerFactory;
import jp.co.ha.common.util.DateFormatPattern;
import jp.co.ha.common.util.DateUtil;

/**
 * 健康情報のDaoクラス
 *
 */
public class HealthInfoDaoImpl extends BaseDaoImpl implements HealthInfoDao {

	private AppLogger logger = AppLoggerFactory.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> selectByUserId(String userId) throws DataBaseException {

		List<HealthInfo> healthInfoList = new ArrayList<HealthInfo>();
		try {
			connect();
			Statement stm = this.con.createStatement();
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE USER_ID = '" + userId + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
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
			e.printStackTrace();
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
	public HealthInfo selectByHealthInfoId(String healthInfoId) throws DataBaseException {

		HealthInfo healthInfo = null;
		try {
			connect();
			Statement stm = this.con.createStatement();
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE HEALTH_INFO_ID = '" + healthInfoId + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
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
			e.printStackTrace();
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
	public void create(HealthInfo healthInfo) throws DuplicateKeyException, DataBaseException {
		logger.info(healthInfo);
		try {
			connect();
			Statement stm = this.con.createStatement();
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
												+ "'" + DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS) + "'"
												+ ")";
			int rs = stm.executeUpdate(sql);
			System.out.println("結果" + rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, TABLE_NAME + "テーブルへのアクセスに失敗しました");
		} finally {
			close();
		}
		logger.info(healthInfo);
	}

}
