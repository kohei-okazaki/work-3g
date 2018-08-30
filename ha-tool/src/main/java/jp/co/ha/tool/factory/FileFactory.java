package jp.co.ha.tool.factory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.ha.tool.config.FileConfig;

public class FileFactory {

	private static final Logger LOG = LoggerFactory.getLogger(FileFactory.class);

	private FileFactory() {
	}

	public static void create(FileConfig conf) {
		LOG.info("作成 開始 ---> " + conf.getFileName());
		File file = new File(conf.getOutputPath() + File.separator + conf.getFileName());

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
