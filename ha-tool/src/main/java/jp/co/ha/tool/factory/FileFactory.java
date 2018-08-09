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

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public void create(FileConfig conf) {
		LOG.info("ファイル作成 開始");
		File file = new File(conf.getOutputPath() + "\\" + conf.getFileName());

		// ファイル作成
		try {
			file.createNewFile();
		} catch (IOException e) {
			LOG.error("", e);
		}

		// ファイル書込
		try (FileWriter fw = new FileWriter(file.getAbsolutePath());
				PrintWriter pw = new PrintWriter(new BufferedWriter(fw));) {
			write(pw, conf.getData());
		} catch (IOException e) {
			LOG.error("", e);
		}
		LOG.info("ファイル作成 終了");
	}

	private void write(PrintWriter pw, String data) {
		pw.println(data);
	}
}
