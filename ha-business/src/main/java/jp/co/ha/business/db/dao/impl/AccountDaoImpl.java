package jp.co.ha.business.db.dao.impl;

import java.sql.SQLException;

import jp.co.ha.business.db.dao.AccountDao;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.common.dao.BaseDao;
import jp.co.ha.common.exception.DataBaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.DateUtil;

/**
 * アカウント情報のDaoクラス
 *
 */
public class AccountDaoImpl extends BaseDao implements AccountDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account selectByUserId(String userId) throws DataBaseException {
		Account account = null;
		try {
			connect();
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE USER_ID = '" + userId + "'";
			execute(sql, SqlType.SELECT);
			while (hasNext()) {
				account = new Account();
				account.setUserId(rs.getString(USER_ID));
				account.setPassword(rs.getString(PASSWORD));
				account.setDeleteFlag(rs.getString(DELETE_FLAG));
				account.setPasswordExpire(rs.getDate(PASSWORD_EXPIRE));
				account.setRemarks(rs.getString(REMARKS));
				account.setApiKey(rs.getString(API_KEY));
				account.setUpdateDate(rs.getTimestamp(UPDATE_DATE));
				account.setRegDate(rs.getTimestamp(REG_DATE));
			}
		} catch (SQLException e) {
			LOG.error(ErrorCode.DB_ACCESS_ERROR.getErrorMessage(), e);
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
	public void create(Account account) throws DataBaseException  {
		LOG.infoRes(account);
		try {
			connect();
			String sql = "INSERT INTO " + TABLE_NAME + " VALUES ("
													+ "'" + account.getUserId() + "', "
													+ "'" + account.getPassword() + "', "
													+ "'" + account.getDeleteFlag() + "', "
													+ "'" + DateUtil.toString(account.getPasswordExpire(), DateFormatType.YYYYMMDD) + "', "
													+ "'" + account.getRemarks() + "', "
													+ "'" + account.getApiKey() + "', "
													+ "'" + DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS) + "', "
													+ "'" + DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS) + "'"
													+ ")";
			execute(sql, SqlType.INSERT);
		} finally {
			close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Account account) throws DataBaseException {
		LOG.infoRes(account);
		try {
			connect();
			String sql = "UPDATE " + TABLE_NAME + " SET "
										+ PASSWORD + "= '" + account.getPassword() + "', "
										+ DELETE_FLAG + "= '" + account.getDeleteFlag() + "', "
										+ PASSWORD_EXPIRE + "= '" + DateUtil.toString(account.getPasswordExpire(), DateFormatType.YYYYMMDD) + "', "
										+ REMARKS + "= '" + account.getRemarks() + "', "
										+ API_KEY + "= '" + account.getApiKey() + "', "
										+ UPDATE_DATE + "= '" + DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS) + "'"
										+ " WHERE "+ USER_ID + "= '" + account.getUserId() + "'";
			execute(sql, SqlType.UPDATE);
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
