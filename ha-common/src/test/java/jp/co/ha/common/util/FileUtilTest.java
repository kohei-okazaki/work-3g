package jp.co.ha.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * {@link FileUtil} のjUnit
 *
 */
public class FileUtilTest {

	@Test
	public void toZipTest() {
		String basePath = "C:\\app\\git\\work-3g\\ha-common";
		String destPath = basePath + "\\src\\test\\resources\\zip\\dest\\result.zip";
		List<File> list = FileUtil.getFileList(basePath + "\\src\\test\\resources\\zip\\src");
		File zip = FileUtil.compressZip(list, destPath);
		assertTrue(zip.isFile());
	}
}
