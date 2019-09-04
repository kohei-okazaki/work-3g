package jp.co.ha.common.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.BeanLoader;
import jp.co.ha.common.util.BeanUtil;

/**
 * Dao実装の基底クラス
 *
 * @deprecated MyBatisを利用するため
 * @since 1.0
 */
public abstract class BaseDao {

	/** LOG */
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
	/** Connection */
	private Connection con;
	/** Statement */
	private Statement stm;
	/** ResultSet */
	protected ResultSet rs;

	/**
	 * DBへ接続を行う
	 *
	 * @throws BaseException
	 *     DBエラー
	 */
	protected void connect() throws BaseException {

		try {
			JdbcConfig conf = BeanLoader.getBean(JdbcConfig.class);
			Class.forName(conf.getDriverClassName()).getDeclaredConstructor().newInstance();
			this.con = DriverManager.getConnection(conf.getUrl(), conf.getUsername(), conf.getPassword());
			LOG.debug("DBに接続");
			if (BeanUtil.notNull(this.con)) {
				this.stm = this.con.createStatement();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new SystemException(CommonErrorCode.DB_ACCESS_ERROR, "JDBCドライバのロードに失敗", e);
		} catch (SQLException e) {
			throw new SystemException(CommonErrorCode.DB_ACCESS_ERROR, "DBに接続に失敗", e);
		} catch (IllegalArgumentException e) {
			throw new SystemException(CommonErrorCode.DB_ACCESS_ERROR, "不正な引数が指定されてます", e);
		} catch (InvocationTargetException e) {
			LOG.error("", e);
		} catch (NoSuchMethodException e) {
			throw new SystemException(CommonErrorCode.DB_ACCESS_ERROR, "DB接続時のコンストラクタが見つかりません", e);
		} catch (SecurityException e) {
			throw new SystemException(CommonErrorCode.DB_ACCESS_ERROR, "DB接続時のコンストラクタの生成に失敗しました", e);
		}
	}

	/**
	 * 次の要素が存在するか返す
	 *
	 * @return 判定結果
	 * @throws BaseException
	 *     SQL実行時に出る例外
	 */
	protected boolean hasNext() throws BaseException {
		try {
			return this.rs.next();
		} catch (SQLException e) {
			throw new SystemException(CommonErrorCode.DB_ACCESS_ERROR, "SQLの実行に失敗しました", e);
		}
	}

	/**
	 * SQLを実行する
	 *
	 * @param sql
	 *     実行するSQL
	 * @param type
	 *     SQL文のタイプ
	 * @return 実行結果
	 * @throws BaseException
	 *     DBエラー
	 */
	protected int execute(String sql, SqlType type) throws BaseException {
		LOG.info("[SQL]--->" + sql);
		try {
			if (SqlType.SELECT == type) {
				this.rs = this.stm.executeQuery(sql);
				return 0;
			} else if (SqlType.INSERT == type || SqlType.UPDATE == type) {
				return this.stm.executeUpdate(sql);
			} else {
				throw new SystemException(CommonErrorCode.DB_SQL_SELECT_ERROR, "実行するSQlが存在しません");
			}
		} catch (SQLException e) {
			throw new SystemException(CommonErrorCode.DB_SQL_EXE_ERROR, "SQLの実行に失敗しました", e);
		}

	}

	/**
	 * close処理を行う
	 *
	 * @throws BaseException
	 *     DBエラー
	 */
	protected void close() throws BaseException {
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
			throw new SystemException(CommonErrorCode.DB_CLOSE_ERROR, "クローズに失敗しました", e);
		}
	}

	/**
	 * SQL種別
	 */
	public static enum SqlType {
		/** insert */
		INSERT,
		/** update */
		UPDATE,
		/** select */
		SELECT,
		/** delete */
		DELETE;
	}
}
