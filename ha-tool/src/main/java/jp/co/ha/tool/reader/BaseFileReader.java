package jp.co.ha.tool.reader;

import java.io.File;

import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;

/**
 * 基底ファイル読込クラス
 *
 * @since 1.0
 */
public abstract class BaseFileReader {

	/**
	 * 指定したパスからファイルを返す
	 *
	 * @param resourcePath
	 *     パス
	 * @return File
	 */
	protected File getFile(String resourcePath) {
		String sysPath = this.getClass().getClassLoader().getResource("").getPath();
		return FileUtil.getFile(sysPath + FileSeparator.SYSTEM.getValue() + resourcePath);
	}
}
