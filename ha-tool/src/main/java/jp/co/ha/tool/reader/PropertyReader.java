package jp.co.ha.tool.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.FileUtil.FileSeparator;

public class PropertyReader extends BaseFileReader {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/**
	 * 指定したプロパティファイル名のPropertiesを返す
	 *
	 * @param propFileName
	 *     プロパティファイル名
	 * @return Properties
	 */
	public Properties getProperty(String propFileName) {
		File propFile = getFile("META-INF" + FileSeparator.SYSTEM.getValue() + propFileName);
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
