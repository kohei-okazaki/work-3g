package jp.co.ha.common.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;

import jp.co.ha.common.exception.DataBaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;

/**
 * Dao実装の基底クラス<br>
 *
 */
public abstract class BaseDaoImpl {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private Connection con;

	private Statement stm;

	protected ResultSet rs;

	/**
	 * DBへ接続を行う<br>
	 */
	protected void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/work3g?serverTimezone=JST", "root", "admin");
			LOGGER.info("DBに接続");
			if (BeanUtil.notNull(con)) {
				stm = con.createStatement();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			LOGGER.error("JDBCドライバのロードに失敗", e);
		} catch (SQLException e) {
			LOGGER.error("DBに接続に失敗", e);
		} catch (IllegalArgumentException e) {
			LOGGER.error("不正な引数が指定", e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 次の要素が存在するか返す<br>
	 *
	 * @return
	 * @throws DataBaseException
	 *     SQL実行時に出る例外
	 */
	protected boolean hasNext() throws DataBaseException {
		try {
			return this.rs.next();
		} catch (SQLException e) {
			LOGGER.error(ErrorCode.DB_ACCESS_ERROR.getErrorMessage(), e);
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, "SQLの実行に失敗しました");
		}
	}

	/**
	 * SQLを実行する<br>
	 *
	 * @param sql
	 *     実行するSQL
	 * @param type
	 *     SQL文のタイプ
	 * @throws SQLException
	 *     SQL実行時に出る例外
	 * @throws DataBaseException
	 *     DBエラー
	 */
	protected int execute(String sql, SqlType type) throws DataBaseException {
		LOGGER.debug("--->" + sql);
		try {
			if (SqlType.SELECT == type) {
				this.rs = this.stm.executeQuery(sql);
				return 0;
			} else if (SqlType.INSERT == type || SqlType.UPDATE == type) {
				return this.stm.executeUpdate(sql);
			} else {
				LOGGER.error(ErrorCode.DB_SQL_SELECT_ERROR.getErrorMessage());
				throw new DataBaseException(ErrorCode.DB_SQL_SELECT_ERROR, "実行するSQlが存在しません");
			}
		} catch (SQLException e) {
			LOGGER.error(ErrorCode.DB_SQL_EXE_ERROR.getErrorMessage(), e);
			throw new DataBaseException(ErrorCode.DB_SQL_EXE_ERROR, "SQLの実行に失敗しました");
		}

	}

	/**
	 * close処理を行う<br>
	 *
	 * @throws DataBaseException
	 *     DBエラー
	 */
	protected void close() throws DataBaseException {
		try {
			if (BeanUtil.notNull(this.stm)) {
				this.stm.close();
				LOGGER.debug("Statementをクローズします");
			}
			if (BeanUtil.notNull(this.rs)) {
				this.rs.close();
				LOGGER.debug("resultsetをクローズします");
			}
			if (BeanUtil.notNull(this.con)) {
				this.con.close();
				LOGGER.debug("DB切断します");
			}
		} catch (SQLException e) {
			LOGGER.error(ErrorCode.DB_CLOSE_ERROR.getErrorMessage(), e);
			throw new DataBaseException(ErrorCode.DB_CLOSE_ERROR, "クローズに失敗しました");
		}
	}

	/**
	 * SQL種別<br>
	 */
	protected enum SqlType {
		INSERT, UPDATE, SELECT, DELETE;
	}
}
