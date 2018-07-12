package jp.co.ha.common.dao;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/work3g?serverTimezone=JST", "root", "adminPass");
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

	protected <T> T getValue(Class<T> clazz, String columnName, ColumnType type) throws SQLException {
		if (ColumnType.BIGDECIMAL.equals(type)) {
			return (T) rs.getBigDecimal(columnName);
		} else if (ColumnType.STRING.equals(type)) {
			return (T) rs.getString(columnName);
		} else if (ColumnType.TIMESTAMP.equals(type)) {
			return (T) rs.getTimestamp(columnName);
		} else if (ColumnType.DATE.equals(type)) {
			return (T) rs.getDate(columnName);
		} else {
			return (T) rs.getObject(columnName);
		}
	}

	protected void executeQuery(String sql) throws SQLException {
		this.rs = stm.executeQuery(sql);
	}

	protected int executeUpdate(String sql) throws SQLException {
		return stm.executeUpdate(sql);
	}

	protected boolean hasNext() throws SQLException {
		return this.rs.next();
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
	public enum ColumnType {
		BIGDECIMAL(BigDecimal.class),
		STRING(String.class),
		TIMESTAMP(java.sql.Timestamp.class),
		DATE(java.util.Date.class);

		private Class<?> clazz;
		private ColumnType(Class<?> clazz) {
			this.clazz = clazz;
		}
		public Class<?> getClazz() {
			return clazz;
		}
	}
}


