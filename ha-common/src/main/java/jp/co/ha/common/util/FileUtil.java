package jp.co.ha.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ファイル操作のUtilクラス
 *
 */
public class FileUtil {

	/**
	 * プライベートコンストラクタ
	 */
	private FileUtil() {
	}

	/**
	 * 指定したファイルパス配下の全ファイルのリストを返す
	 *
	 * @param path
	 *     ファイルパス
	 * @return ファイルパス配下に存在する全ファイル
	 */
	public static List<File> getAllFile(String path) {
		List<File> fileList = new ArrayList<>();
		for (File file : new File(path).listFiles()) {
			if (file.isFile()) {
				fileList.add(file);
			} else {
				fileList.addAll(getFileList(file));
			}
		}
		return fileList;
	}

	/**
	 * 指定したファイル配下の全ファイルのリストを返す
	 *
	 * @param file
	 *     ファイル
	 * @return ファイル配下に存在する全ファイル
	 */
	public static List<File> getFileList(File file) {
		List<File> fileList = new ArrayList<>();
		for (File childFile : file.listFiles()) {
			if (childFile.isFile()) {
				fileList.add(childFile);
			} else {
				fileList.addAll(getFileList(childFile));
			}
		}
		return fileList;
	}

	/**
	 * 指定した<code>srcFileList</code>を<code>zipFile</code>に圧縮する<br>
	 *
	 * @param srcFileList
	 *     zipファイルに含めたいファイルのリスト
	 * @param destFilePath 出力先ファイルパス
	 * @return zipファイル
	 */
	public static File toZip(List<File> srcFileList, String destFilePath) {
		File zipFile = new File(destFilePath);
		try (FileOutputStream fos = new FileOutputStream(zipFile.getName());
				ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile.getName()));
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {

			for (File srcFile : srcFileList) {
				ZipEntry entry = new ZipEntry(srcFile.getName());
				zos.putNextEntry(entry);
				if (srcFile.getName().endsWith(File.separator)) {
					zos.closeEntry();
					continue;
				}

				try (FileInputStream fis = new FileInputStream(destFilePath + File.separator + srcFile);
						BufferedInputStream bis = new BufferedInputStream(fis)) {
					int size = 0;
					byte[] buffer = new byte[1024];
					while ((size = bis.read(buffer)) > 0) {
						zos.write(buffer, 0, size);
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return zipFile;
	}

	public static List<File> unZip(File srcZipFile) {
		List<File> destFileList = new ArrayList<>();

		return destFileList;
	}

	/**
	 * ファイル拡張子の列挙
	 */
	public static enum FileSuffix {

		/** csv */
		CSV(".csv"),
		/** zip */
		ZIP(".zip"),
		/** java */
		JAVA(".java"),
		/** sql */
		SQL(".sql");

		/** 値 */
		private String value;

		/**
		 * コンストラクタ
		 *
		 * @param value
		 *     値
		 */
		private FileSuffix(String value) {
			this.value = value;
		}

		/**
		 * 値を返す
		 *
		 * @return value
		 */
		public String getValue() {
			return this.value;
		}

		/**
		 * 指定したファイル拡張子列挙が自身と一致するか判定する<br>
		 * 一致する場合true, それ以外の場合false<br>
		 *
		 * @param suffix
		 *     ファイル拡張子列挙
		 * @return 判定結果
		 */
		public boolean is(FileSuffix suffix) {
			return this == suffix;
		}
	}
}
