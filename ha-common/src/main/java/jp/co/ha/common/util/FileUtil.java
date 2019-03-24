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
import jp.co.ha.common.type.BaseEnum;

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
	 * 指定した<code>srcFileList</code>を<code>zipFile</code>に圧縮する
	 *
	 * @param srcFileList
	 *     zipファイルに含めたいファイルのリスト
	 * @param destFilePath
	 *     出力先ファイルパス
	 * @return zipファイル
	 */
	public static File toZip(List<File> srcFileList, String destFilePath) {
		// TODO
		File zipFile = getFile(destFilePath);
		try (FileOutputStream fos = new FileOutputStream(zipFile.getName());
				ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile.getName()));
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {

			for (File srcFile : srcFileList) {
				ZipEntry entry = new ZipEntry(srcFile.getName());
				zos.putNextEntry(entry);
				if (srcFile.getName().endsWith(FileSeparator.SYSTEM.getValue())) {
					zos.closeEntry();
					continue;
				}

				try (FileInputStream fis = new FileInputStream(destFilePath + FileSeparator.SYSTEM.getValue() + srcFile);
						BufferedInputStream bis = new BufferedInputStream(fis)) {
					int size = 0;
					byte[] buffer = new byte[1024];
					while ((size = bis.read(buffer)) > 0) {
						zos.write(buffer, 0, size);
					}
				}
			}
		} catch (FileNotFoundException e) {
			srcFileList.stream().forEach(srcFile -> LOG.warn(srcFile.getPath(), e));
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
			if (srcPath.contains(FileSeparator.WINDOWS.getValue())) {
				return getFile(srcPath);
			} else {
				return getFile(srcPath.replaceAll(FileSeparator.LINUX.getValue(), FileSeparator.WINDOWS.getValue()));
			}
		} else {
			if (srcPath.contains(FileSeparator.LINUX.getValue())) {
				return getFile(srcPath);
			} else {
				return getFile(srcPath.replaceAll(FileSeparator.WINDOWS.getValue(), FileSeparator.LINUX.getValue()));
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
	 * 指定した<code>path</code>までのファイルオブジェクトが存在するかどうか判定する
	 *
	 * @param path
	 *     パス
	 * @return 存在するtrue, それ以外の場合false
	 */
	public static boolean isExists(String path) {
		return getFile(path).exists();
	}

	/**
	 * 指定した<code>path</code>までのディレクトリを作成する
	 *
	 * @param path
	 *     パス
	 * @return 作成された場合true, それ以外の場合false
	 */
	public static boolean mkdir(String path) {
		return getFile(path).mkdir();
	}

	/**
	 * ファイル拡張子の列挙
	 */
	public static enum FileExtension implements BaseEnum {

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
		private FileExtension(String value) {
			this.value = value;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getValue() {
			return this.value;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean is(String value) {
			return this.value.equals(value);
		}

		/**
		 * 指定したファイル拡張子列挙が自身と一致するか判定する<br>
		 * 一致する場合true, それ以外の場合false<br>
		 *
		 * @param fileExtension
		 *     ファイル拡張子列挙
		 * @return 判定結果
		 */
		public boolean is(FileExtension fileExtension) {
			return this == fileExtension;
		}

		/**
		 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
		 * @param value
		 *     値
		 * @return FileExtension
		 */
		public static FileExtension of(String value) {
			return BaseEnum.of(FileExtension.class, value);
		}
	}

	/**
	 * ファイルセパレータの列挙
	 */
	public static enum FileSeparator implements BaseEnum {

		/** Windows */
		WINDOWS("\\"),
		/** linux */
		LINUX("/"),
		/** system */
		SYSTEM(FileSystems.getDefault().getSeparator());

		/**
		 * コンストラクタ
		 *
		 * @param value
		 *     セパレータ
		 */
		private FileSeparator(String value) {
			this.value = value;
		}

		/** セパレータ */
		private String value;

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

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getValue() {
			return this.value;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean is(String value) {
			return this.value.equals(value);
		}

		/**
		 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
		 * @param value
		 *     値
		 * @return FileSeparator
		 */
		public static FileSeparator of(String value) {
			return BaseEnum.of(FileSeparator.class, value);
		}

	}
}
