package jp.co.ha.common.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;

/**
 * Dao設定ファイル情報保持クラス<br>
 * jdbc.propertiesの読み込みを行う<br>
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

	public static JdbcConfig getInstance() {
		return instance;
	}

	/**
	 * 初期化処理<br>
	 */
	private void init() {
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		String propertiesPath = "META-INF" + File.separator + "jdbc.properties";
		readProperty(new File(classPath, propertiesPath));
	}

	/**
	 * プロパティファイルを読込<br>
	 *
	 * @param propFile
	 *     プロパティファイル
	 */
	private void readProperty(File propFile) {
		Properties prop = new Properties();
		try (InputStream is = new FileInputStream(propFile.getAbsolutePath())) {
			prop.load(is);
			this.driverClassName = prop.getProperty("driverClassName");
			this.url = prop.getProperty("url");
			this.username = prop.getProperty("username");
			this.password = prop.getProperty("password");
		} catch (FileNotFoundException e) {
			LOG.error("ファイルが見つかりません ファイル名：" + propFile.getName(), e);
		} catch (IOException e) {
			LOG.error("", e);
		}
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
