package jp.co.ha.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * ファイル操作のUtilクラス
 *
 */
public class FileUtil {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

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
	public static List<File> getFileList(String path) {
		return getFileList(getFile(path));
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
	 * @param destFilePath
	 *     出力先ファイルパス
	 * @return zipファイル
	 */
	public static File toZip(List<File> srcFileList, String destFilePath) {
		File zipFile = getFile(destFilePath);
		try (FileOutputStream fos = new FileOutputStream(zipFile.getName());
				ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile.getName()));
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {

			for (File srcFile : srcFileList) {
				ZipEntry entry = new ZipEntry(srcFile.getName());
				zos.putNextEntry(entry);
				if (srcFile.getName().endsWith(FileSeparator.SYSTEM.getSeparator())) {
					zos.closeEntry();
					continue;
				}

				try (FileInputStream fis = new FileInputStream(destFilePath + FileSeparator.SYSTEM.getSeparator() + srcFile);
						BufferedInputStream bis = new BufferedInputStream(fis)) {
					int size = 0;
					byte[] buffer = new byte[1024];
					while ((size = bis.read(buffer)) > 0) {
						zos.write(buffer, 0, size);
					}
				}
			}
		} catch (FileNotFoundException e) {
			srcFileList.stream().forEach(srcFile -> {
				LOG.warn(srcFile.getPath(), e);
			});
			LOG.warn("ファイルが見つかりません destFile:" + destFilePath, e);
		} catch (IOException e) {
			LOG.warn("Zipへの圧縮に失敗しました", e);
		}
		return zipFile;
	}

	public static List<File> unZip(File srcZipFile) {
		// TODO
		List<File> destFileList = new ArrayList<>();

		return destFileList;
	}

	/**
	 * ファイルのコピーを行う
	 *
	 * @param srcFile
	 *     コピー元ファイル
	 * @param destFile
	 *     コピー先ファイル
	 */
	public static void copyFile(File srcFile, File destFile) {
		try (FileInputStream fis = new FileInputStream(srcFile);
				FileOutputStream fos = new FileOutputStream(destFile)) {

			byte[] buf = new byte[1024];
			int length = 0;

			// Fileの終わりまで読込
			while ((length = fis.read(buf)) != -1) {
				fos.write(buf);
				fos.flush();
			}
		} catch (FileNotFoundException e) {
			LOG.warn("ファイルが見つかりません srcFile:" + srcFile.getPath() + " destFile:" + destFile.getPath(), e);
		} catch (IOException e) {
			LOG.warn("ファイルの書き込みや読み込みに失敗しました", e);
		}
	}

	/**
	 * ファイルのコピーを行う
	 *
	 * @param srcPath
	 *     コピー元ファイル
	 * @param destPath
	 *     コピー先ファイル
	 */
	public static void copyFile(String srcPath, String destPath) {
		Path src = Paths.get(srcPath);
		Path dest = Paths.get(destPath);
		try {
			Files.deleteIfExists(dest);
			Files.copy(src, dest);
		} catch (IOException e) {
			LOG.warn("ファイルのコピーに失敗しました srcFilePath:" + srcPath + " destFilePath:" + destPath, e);
		}
	}

	/**
	 * 指定された<code>srcPath</code>を指定された<code>sep</code>のパスに変換する
	 *
	 * @param srcPath
	 *     パス
	 * @param sep
	 *     ファイルセパレータ
	 * @return ファイル
	 */
	public static File convertPathFile(String srcPath, FileSeparator sep) {
		if (FileSeparator.WINDOWS.is(sep)) {
			if (srcPath.contains(FileSeparator.WINDOWS.getSeparator())) {
				return getFile(srcPath);
			} else {
				return getFile(srcPath.replaceAll(FileSeparator.LINUX.getSeparator(), FileSeparator.WINDOWS.getSeparator()));
			}
		} else {
			if (srcPath.contains(FileSeparator.LINUX.getSeparator())) {
				return getFile(srcPath);
			} else {
				return getFile(srcPath.replaceAll(FileSeparator.WINDOWS.getSeparator(), FileSeparator.LINUX.getSeparator()));
			}
		}
	}

	/**
	 * 指定した<code>path</code>のファイルオブジェクトを返す
	 *
	 * @param path
	 *     パス
	 * @return ファイル
	 */
	public static File getFile(String path) {
		return new File(path);
	}

	/**
	 * 指定した<code>path</code>がファイルかどうかを返す<br>
	 *
	 * @param path
	 *     パス
	 * @return ファイルの場合true, それ以外の場合false
	 */
	public static boolean isFile(String path) {
		return getFile(path).isFile();
	}

	/**
	 * 指定した<code>path</code>がディレクトリかどうかを返す<br>
	 *
	 * @param path
	 *     パス
	 * @return ディレクトリの場合true, それ以外の場合false
	 */
	public static boolean isDir(String path) {
		return getFile(path).isDirectory();
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

	/**
	 * ファイルセパレータの列挙
	 */
	public static enum FileSeparator {

		/** Windows */
		WINDOWS("\\"),
		/** linux */
		LINUX("/"),
		/** system */
		SYSTEM(FileSystems.getDefault().getSeparator());

		/**
		 * コンストラクタ
		 *
		 * @param separator
		 *     セパレータ
		 */
		private FileSeparator(String separator) {
			this.separator = separator;
		}

		/** セパレータ */
		private String separator;

		/**
		 * セパレータを返す
		 *
		 * @return separator
		 */
		public String getSeparator() {
			return this.separator;
		}

		/**
		 * 指定したファイルセパレータが自身と一致するかどうか返す<br>
		 *
		 * @param separator
		 *     セパレータ
		 * @return 判定結果
		 */
		public boolean is(FileSeparator separator) {
			return this == separator;
		}

	}
}
