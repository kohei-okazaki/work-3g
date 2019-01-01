package jp.co.ha.tool.factory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.tool.config.FileConfig;

/**
 * ファイル作成クラス
 *
 */
public class FileFactory {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(FileFactory.class);

	private FileFactory() {
	}

	/**
	 * 指定されたファイル設定情報からファイルを作成する
	 *
	 * @param conf
	 *     ファイル設定情報
	 */
	public static void create(FileConfig conf) {
		LOG.info("作成 開始 ---> " + conf.getFileName());
		File file = FileUtil.getFile(conf.getOutputPath() + FileSeparator.SYSTEM.getValue() + conf.getFileName());

		// ファイル作成
		try {
			file.createNewFile();
		} catch (IOException e) {
			LOG.error("", e);
		}

		// ファイル書込
		try (FileWriter fw = new FileWriter(file.getAbsolutePath());
				PrintWriter pw = new PrintWriter(new BufferedWriter(fw));) {
			pw.println(conf.getData());
			pw.flush();
		} catch (IOException e) {
			LOG.error("", e);
		}
		LOG.info("作成 終了 ---> " + conf.getFileName());
	}

}
