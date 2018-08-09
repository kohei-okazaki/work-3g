package jp.co.ha.tool.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyReader extends BaseFileReader {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public Properties getProperty(String propFileName) {
		File propFile = getFilePath("META-INF/" + propFileName);
		Properties prop = new Properties();
		try (InputStream is = new FileInputStream(propFile.getAbsolutePath())) {
			prop.load(is);
		} catch (FileNotFoundException e) {
			LOG.error("プロパティファイル読込エラー", e);
		} catch (IOException e) {
			LOG.error("読込エラー", e);
		}
		return prop;
	}
}
