package jp.co.ha.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.dao.BaseDaoImpl;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.exception.DataBaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.AppLoggerFactory;
import jp.co.ha.common.util.DateFormatPattern;
import jp.co.ha.common.util.DateUtil;

/**
 * アカウント情報のDaoクラス
 *
 */
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {

	private AppLogger logger = AppLoggerFactory.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account selectByUserId(String userId) throws DataBaseException {

		Account account = null;

		try {
			connect();
			Statement stm = this.con.createStatement();
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE USER_ID = '" + userId + "'";
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				account = new Account();
				account.setUserId(rs.getString(USER_ID));
				account.setPassword(rs.getString(PASSWORD));
				account.setDeleteFlag(rs.getString(DELETE_FLAG));
				account.setPasswordExpire(rs.getDate(PASSWORD_EXPIRE));
				account.setRemarks(rs.getString(REMARKS));
				account.setFileEnclosureCharFlag(rs.getString(FILE_ENCLOSURE_CHAR_FLAG));
				account.setHealthInfoMaskFlag(rs.getString(HEALTH_INFO_MASK_FLAG));
				account.setUpdateDate(rs.getTimestamp(UPDATE_DATE));
				account.setRegDate(rs.getTimestamp(REG_DATE));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, TABLE_NAME + "テーブルへのアクセスに失敗しました");
		} finally {
			close();
		}
		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(Account account) throws DuplicateKeyException, DataBaseException  {

		logger.info(account);

		try {
			connect();
			Statement stm = this.con.createStatement();
			String sql = "INSERT INTO " + TABLE_NAME + " VALUES ("
													+ "'" + account.getUserId() + "', "
													+ "'" + account.getPassword() + "', "
													+ "'" + account.getDeleteFlag() + "', "
													+ "'" + DateUtil.toString(account.getPasswordExpire(), DateFormatPattern.YYYYMMDD) + "', "
													+ "'" + account.getRemarks() + "', "
													+ "'" + account.getFileEnclosureCharFlag() + "', "
													+ "'" + account.getHealthInfoMaskFlag() + "', "
													+ "'" + DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS) + "', "
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
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Account account) throws DataBaseException {

		logger.info(account);

		try {
			connect();
			Statement stm = this.con.createStatement();
			String sql = "UPDATE " + TABLE_NAME + " SET "
										+ PASSWORD + "= '" + account.getPassword() + "', "
										+ DELETE_FLAG + "= '" + account.getDeleteFlag() + "', "
										+ PASSWORD_EXPIRE + "= '" + DateUtil.toString(account.getPasswordExpire(), DateFormatPattern.YYYYMMDD) + "', "
										+ REMARKS + "= '" + account.getRemarks() + "', "
										+ FILE_ENCLOSURE_CHAR_FLAG + "= '" + account.getFileEnclosureCharFlag() + "', "
										+ HEALTH_INFO_MASK_FLAG + "= '" + account.getHealthInfoMaskFlag() + "', "
										+ UPDATE_DATE + "= '" + DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS) + "'"
										+ " WHERE "+ USER_ID + "= '" + account.getUserId() + "'";
			System.out.println(sql);
			int rs = stm.executeUpdate(sql);
			System.out.println("結果" + rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, TABLE_NAME + "テーブルへのアクセスに失敗しました");
		} finally {
			close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(String userId) {
		// TODO 削除処理を追加すること
	}

}
