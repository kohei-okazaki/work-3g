package jp.co.ha.tool.reader;

import java.io.File;

/**
 * 基底ファイル読込クラス
 *
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
		return new File(sysPath, resourcePath);
	}
}
