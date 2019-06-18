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

		String destPath = "C:\\app\\pleiades\\workspace\\java-11\\file\\result\\sample.zip";
		List<File> list = FileUtil.getFileList("C:\\app\\pleiades\\workspace\\java-11\\file\\zip");
		File zip = FileUtil.compressZip(list, destPath);
		assertTrue(zip.isFile());
	}
}
