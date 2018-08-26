package jp.co.ha.common.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jp.co.ha.common.util.BeanUtil;

/**
 * Dao設定ファイル情報保持クラス<br>
 * jdbc.propertiesの読み込みを行う<br>
 *
 */
public class DaoConfig {

	private static final DaoConfig instance = new DaoConfig();

	private String driverClassName;
	private String url;
	private String username;
	private String password;

	private DaoConfig() {
		if (BeanUtil.isNull(instance)) {
			init();
		}
	}

	public static DaoConfig getInstance() {
		return instance;
	}

	private void init() {
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		String propertiesPath = "META-INF" + File.separator + "jdbc.properties";
		readProperty(new File(classPath, propertiesPath));
	}

	private void readProperty(File propFile) {
		Properties prop = new Properties();
		try (InputStream is = new FileInputStream(propFile.getAbsolutePath())) {
			prop.load(is);
			this.driverClassName = prop.getProperty("driverClassName");
			this.url = prop.getProperty("url");
			this.username = prop.getProperty("username");
			this.password = prop.getProperty("password");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
