package jp.co.ha.tool.read;

import java.io.File;

public abstract class BaseFileReader {

	/**
	 * 指定したresoucePathを返す<br>
	 *
	 * @param resourcePath
	 *     META-INF/ファイル名
	 * @return
	 */
	protected File getFilePath(String resourcePath) {
		String sysPath = this.getClass().getClassLoader().getResource("").getPath();
		return new File(sysPath, resourcePath);
	}
}
