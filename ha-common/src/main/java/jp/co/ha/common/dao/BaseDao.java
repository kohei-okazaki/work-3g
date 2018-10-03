package jp.co.ha.common.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.ha.common.exception.DataBaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;

/**
 * Dao実装の基底クラス<br>
 *
 */
public abstract class BaseDao {

	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private Connection con;
	private Statement stm;
	protected ResultSet rs;

	/**
	 * DBへ接続を行う<br>
	 *
	 * @throws DataBaseException
	 *     DBエラー
	 */
	protected void connect() throws DataBaseException {

		try {
			JdbcConfig prop = JdbcConfig.getInstance();
			Class.forName(prop.getDriverClassName()).getDeclaredConstructor().newInstance();
			this.con = DriverManager.getConnection(prop.getUrl(), prop.getUsername(), prop.getPassword());
			LOG.debug("DBに接続");
			if (BeanUtil.notNull(this.con)) {
				this.stm = this.con.createStatement();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, "JDBCドライバのロードに失敗");
		} catch (SQLException e) {
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, "DBに接続に失敗");
		} catch (IllegalArgumentException e) {
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, "不正な引数が指定されてます");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, "DB接続時のコンストラクタが見つかりません");
		} catch (SecurityException e) {
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, "DB接続時のコンストラクタの生成に失敗しました");
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
	 * @throws DataBaseException
	 *     DBエラー
	 */
	protected int execute(String sql, SqlType type) throws DataBaseException {
		LOG.info("[SQL]--->" + sql);
		try {
			if (SqlType.SELECT == type) {
				this.rs = this.stm.executeQuery(sql);
				return 0;
			} else if (SqlType.INSERT == type || SqlType.UPDATE == type) {
				return this.stm.executeUpdate(sql);
			} else {
				throw new DataBaseException(ErrorCode.DB_SQL_SELECT_ERROR, "実行するSQlが存在しません");
			}
		} catch (SQLException e) {
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
				LOG.debug("Statementをクローズします");
			}
			if (BeanUtil.notNull(this.rs)) {
				this.rs.close();
				LOG.debug("resultsetをクローズします");
			}
			if (BeanUtil.notNull(this.con)) {
				this.con.close();
				LOG.debug("DB切断します");
			}
		} catch (SQLException e) {
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
