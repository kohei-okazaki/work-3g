package jp.co.ha.common.io.file.property.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.FileUtil.FileSeparator;

public class PropertyReader {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(PropertyReader.class);

	/**
	 * 指定したパスのプロパティファイルの読み込みを行う
	 *
	 * @param path
	 *     ファイルパス
	 * @param fileName
	 *     ファイル名
	 * @return Properties
	 */
	public Properties read(String path, String fileName) {

		LOG.debug(fileName + "の読み込み 開始");

		Properties prop = new Properties();
		try (InputStream is = new FileInputStream(path + FileSeparator.SYSTEM.getValue() + fileName)) {
			prop.load(is);
		} catch (FileNotFoundException e) {
			LOG.error("プロパティファイル読込エラー ファイル名=" + fileName, e);
		} catch (IOException e) {
			LOG.error("読込エラー", e);
		} finally {
			LOG.debug(fileName + "の読み込み 終了");
		}
		return prop;
	}

}
