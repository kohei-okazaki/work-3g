package jp.co.ha.common.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;

/**
 * Dao設定ファイル情報保持クラス<br>
 * jdbc.propertiesの読み込みを行う
 *
 */
public class JdbcConfig {

	private static final Logger LOG = LoggerFactory.getLogger(JdbcConfig.class);

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
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		String propertiesPath = "META-INF" + FileSeparator.SYSTEM.getValue() + "jdbc.properties";
		readProperty(FileUtil.getFile(classPath + FileSeparator.SYSTEM.getValue() + propertiesPath));
	}

	/**
	 * プロパティファイルを読込
	 *
	 * @param propFile
	 *     プロパティファイル
	 */
	private void readProperty(File propFile) {
		Properties prop = new Properties();
		try (InputStream is = new FileInputStream(propFile.getAbsolutePath())) {
			prop.load(is);
			this.driverClassName = prop.getProperty("jdbc.driverClassName");
			this.url = prop.getProperty("jdbc.url");
			this.username = prop.getProperty("jdbc.username");
			this.password = prop.getProperty("jdbc.password");
		} catch (FileNotFoundException e) {
			LOG.error("ファイルが見つかりません ファイル名：" + propFile.getName(), e);
		} catch (IOException e) {
			LOG.error("", e);
		}
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
