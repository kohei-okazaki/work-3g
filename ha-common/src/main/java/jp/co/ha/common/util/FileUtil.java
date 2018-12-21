package jp.co.ha.common.util;

import java.io.File;
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
	 * 指定した<code>srcFileList</code>を<code>zipFile</code>に圧縮する<br>
	 *
	 * @param srcFileList
	 *     zipファイルに含めたいファイルのリスト
	 * @param zipFile
	 *     zipファイル
	 */
	public static void toZip(List<File> srcFileList, File zipFile) {

		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile.getName()))) {
			byte[] buffer = new byte[1024];
			for (File srcFile : srcFileList) {
				ZipEntry entry = new ZipEntry(srcFile.getName());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
