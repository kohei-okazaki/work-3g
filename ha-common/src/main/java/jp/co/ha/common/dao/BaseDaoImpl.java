package jp.co.ha.common.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.ha.common.exception.DataBaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.util.BeanUtil;

/**
 * Dao実装の基底クラス<br>
 *
 */
public abstract class BaseDaoImpl {

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
			System.out.println("MySQLに接続できました。");
			if (BeanUtil.notNull(con)) {
				stm = con.createStatement();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("JDBCドライバのロードに失敗しました。");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MySQLに接続できませんでした。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
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
	 * @throws SQLException
	 *     SQL実行時に出る例外
	 */
	protected boolean hasNext() throws SQLException {
		return this.rs.next();
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
	 */
	protected int execute(String sql, SqlType type) throws SQLException {
		if (SqlType.SELECT == type) {
			this.rs = this.stm.executeQuery(sql);
			return 0;
		} else if (SqlType.INSERT == type || SqlType.UPDATE == type) {
			return this.stm.executeUpdate(sql);
		} else {
			throw new DataBaseException(ErrorCode.DB_ACCESS_ERROR, "実行するSQlが存在しません");
		}
	}

	/**
	 * close処理を行う<br>
	 */
	protected void close() {
		try {
			if (BeanUtil.notNull(stm)) {
				stm.close();
				System.out.println("Statementをクローズします");
			}
			if (BeanUtil.notNull(rs)) {
				rs.close();
				System.out.println("resultsetをクローズします");
			}
			if (BeanUtil.notNull(con)) {
				con.close();
				System.out.println("接続をクローズします");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MySQLのクローズに失敗しました。");
		}
	}

	protected enum SqlType {
		INSERT, UPDATE, SELECT, DELETE;
	}
}
