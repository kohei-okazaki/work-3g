package jp.co.ha.tool.reader;

import java.io.File;

public abstract class BaseFileReader {

	protected File getFilePath(String resourcePath) {
		String sysPath = this.getClass().getClassLoader().getResource("").getPath();
		return new File(sysPath, resourcePath);
	}
}
