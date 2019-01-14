package jp.co.ha.common.db;

import java.util.Properties;

import jp.co.ha.common.io.file.property.reader.PropertyReader;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;

/**
 * Dao設定ファイル情報保持クラス<br>
 * jdbc.propertiesの読み込みを行う
 *
 */
public class JdbcConfig {

	/** JdbcConfig:instance */
	private static final JdbcConfig instance = new JdbcConfig();

	private String driverClassName;
	private String url;
	private String username;
	private String password;

	private JdbcConfig() {
		if (BeanUtil.isNull(instance)) {
			init();
		}
	}

	/**
	 * JdbcConfigを返す
	 *
	 * @return instance
	 */
	public static JdbcConfig getInstance() {
		return instance;
	}

	/**
	 * 初期化処理
	 */
	private void init() {
		String resourcePath = this.getClass().getClassLoader().getResource("").getPath() + FileSeparator.SYSTEM.getValue() + "META-INF";
		Properties prop = new PropertyReader().read(resourcePath, "jdbc.properties");
		this.driverClassName = prop.getProperty("jdbc.driverClassName");
		this.url = prop.getProperty("jdbc.url");
		this.username = prop.getProperty("jdbc.username");
		this.password = prop.getProperty("jdbc.password");
	}

	/**
	 * driverClassNameを返す
	 *
	 * @return driverClassName
	 */
	public String getDriverClassName() {
		return driverClassName;
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
	 * usernameを返す
	 *
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * passwordを返す
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

}
