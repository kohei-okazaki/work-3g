package jp.co.ha.common.db;

import org.springframework.stereotype.Component;

/**
 * JDBC設定ファイル情報保持クラス
 *
 */
@Component
public class JdbcConfig {

	private String driverClassName;
	private String url;
	private String username;
	private String password;

	/**
	 * driverClassNameを返す
	 *
	 * @return driverClassName
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * driverClassNameを設定する
	 *
	 * @param driverClassName
	 *     driverClassName
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * urlを返す
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * urlを設定する
	 *
	 * @param url
	 *     url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * usernameを返す
	 *
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * usernameを設定する
	 *
	 * @param username
	 *     username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * passwordを返す
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * passwordを設定する
	 *
	 * @param password
	 *     password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
