package jp.co.ha.business.db.dao.impl;

import java.sql.SQLException;

import jp.co.ha.business.db.dao.HealthInfoFileSettingDao;
import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.common.dao.BaseDao;
import jp.co.ha.common.exception.DataBaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.DateUtil;

/**
 * 健康情報ファイル設定Dao実装クラス<br>
 *
 */
public class HealthInfoFileSettingDaoImpl extends BaseDao implements HealthInfoFileSettingDao {

	private final AppLogger APP_LOGGER = LoggerFactory.getAppLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoFileSetting selectByUserId(String userId) throws DataBaseException {

		HealthInfoFileSetting entity = null;
		try {
			connect();
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE USER_ID = '" + userId + "'";
			execute(sql, SqlType.SELECT);
			while (hasNext()) {
				entity = new HealthInfoFileSetting();
				entity.setUserId(rs.getString(USER_ID));
				entity.setHeaderFlag(rs.getString(HEADER_FLAG));
				entity.setFooterFlag(rs.getString(FOOTER_FLAG));
				entity.setMaskFlag(rs.getString(MASK_FLAG));
				entity.setEnclosureCharFlag(rs.getString(ENCLOSURE_CHAR_FLAG));
				entity.setUpdateDate(rs.getTimestamp(UPDATE_DATE));
				entity.setRegDate(rs.getTimestamp(REG_DATE));
			}
		} catch (SQLException e) {
			LOG.error(ErrorCode.DB_ACCESS_ERROR.getErrorMessage(), e);
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, TABLE_NAME + "テーブルへのアクセスに失敗しました");
		} finally {
			close();
		}
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(HealthInfoFileSetting healthInfoFileSetting) throws DataBaseException {
		APP_LOGGER.info(healthInfoFileSetting);
		try {
			connect();
			String sql = "INSERT INTO " + TABLE_NAME + " VALUES ("
					+ "'" + healthInfoFileSetting.getUserId() + "', "
					+ "'" + healthInfoFileSetting.getHeaderFlag() + "', "
					+ "'" + healthInfoFileSetting.getFooterFlag() + "', "
					+ "'" + healthInfoFileSetting.getMaskFlag() + "', "
					+ "'" + healthInfoFileSetting.getEnclosureCharFlag() + "', "
					+ "'" + DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS) + "', "
					+ "'" + DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS) + "'"
					+ ")";
			int rs = execute(sql, SqlType.INSERT);
			System.out.println("結果" + rs);
		} finally {
			close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(HealthInfoFileSetting healthInfoFileSetting) throws DataBaseException {
		APP_LOGGER.info(healthInfoFileSetting);
		try {
			connect();
			String sql = "UPDATE " + TABLE_NAME + " SET "
					+ HEADER_FLAG + "= '" + healthInfoFileSetting.getHeaderFlag() + "', "
					+ FOOTER_FLAG + "= '" + healthInfoFileSetting.getFooterFlag() + "', "
					+ MASK_FLAG + "= '" + healthInfoFileSetting.getMaskFlag() + "', "
					+ ENCLOSURE_CHAR_FLAG + "= '" + healthInfoFileSetting.getEnclosureCharFlag() + "', "
					+ UPDATE_DATE + "= '" + DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS) + "'"
					+ " WHERE "+ USER_ID + "= '" + healthInfoFileSetting.getUserId() + "'";
			System.out.println(sql);
			int rs = execute(sql, SqlType.UPDATE);
			System.out.println("結果" + rs);
		} finally {
			close();
		}
	}

}
