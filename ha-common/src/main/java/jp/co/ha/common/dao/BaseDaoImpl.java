package jp.co.ha.common.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Dao実装の基底クラス<br>
 *
 */
public abstract class BaseDaoImpl {

	protected Connection con;

	/**
	 * DBへ接続を行う<br>
	 */
	protected void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/work3g?serverTimezone=JST", "root", "admin");
			System.out.println("MySQLに接続できました。");
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
	 * close処理を行う<br>
	 */
	protected void close() {
		if (con != null) {
			try {
				con.close();
				System.out.println("接続をクローズします");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("MySQLのクローズに失敗しました。");
			}
		}
	}
}
