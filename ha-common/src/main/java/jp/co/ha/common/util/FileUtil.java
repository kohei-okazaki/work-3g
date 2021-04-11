package jp.co.ha.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;

/**
 * ファイル操作のUtilクラス
 *
 * @version 1.0.0
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
     * ファイルを作成する<br>
     * ファイルが存在しない場合のみ、作成
     *
     * @param filePath
     *     ファイルパス
     * @return ファイルのパスクラス
     * @throws IOException
     *     ファイル作成処理に失敗した場合
     */
    public static Path createFile(String filePath) throws IOException {

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            // ファイルが存在する場合
            return path;
        }
        return Files.createFile(path);
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
        if (file == null) {
            return fileList;
        }
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
    public static File compressZip(List<File> srcFileList, String destFilePath) {

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destFilePath),
                Charset.forName(jp.co.ha.common.type.Charset.UTF_8.getValue()))) {
            byte[] buffer = new byte[1024];
            for (File file : srcFileList) {
                try (InputStream in = new FileInputStream(file)) {
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    int len = 0;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOG.error("ファイルが見つかりません destFile:" + destFilePath, e);
        } catch (IOException e) {
            LOG.error("Zipへの圧縮に失敗しました destFile:" + destFilePath, e);
        }
        return new File(destFilePath);
    }

    /**
     * 指定されたZipを解凍し、中のファイルを返す
     *
     * @param srcFile
     *     ZIPファイル
     * @return 解凍後のファイル
     */
    public static List<File> unCompressZip(File srcFile) {
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
     * @throws BaseException
     */
    public static void copyFile(File srcFile, File destFile) throws BaseException {
        try (FileInputStream fis = new FileInputStream(srcFile);
                FileChannel srcChannel = fis.getChannel();
                FileOutputStream fos = new FileOutputStream(destFile);
                FileChannel destChannel = fos.getChannel()) {
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
        } catch (FileNotFoundException e) {
            throw new SystemException(CommonErrorCode.FILE_OR_DIR_ERROR,
                    "ファイルが見つかりません srcFile:" + srcFile.getPath() + " destFile:"
                            + destFile.getPath(),
                    e);
        } catch (IOException e) {
            throw new SystemException(CommonErrorCode.FILE_OR_DIR_ERROR,
                    "ファイルの書き込みや読み込みに失敗しました", e);
        }
    }

    /**
     * ファイルのコピーを行う
     *
     * @param srcPath
     *     コピー元ファイル
     * @param destPath
     *     コピー先ファイル
     * @throws BaseException
     *     ファイルのコピーに失敗した場合
     */
    public static void copyFile(String srcPath, String destPath) throws BaseException {
        try {
            Path src = Paths.get(srcPath);
            Path dest = Paths.get(destPath);
            Files.deleteIfExists(dest);
            Files.copy(src, dest);
        } catch (IOException e) {
            throw new SystemException(CommonErrorCode.FILE_OR_DIR_ERROR,
                    "ファイルのコピーに失敗しました srcFilePath:" + srcPath + " destFilePath:"
                            + destPath,
                    e);
        }
    }

    /**
     * 指定したパス配下のファイルをすべて削除する
     *
     * @param path
     *     対象パス
     */
    public static void deleteFiles(String path) {

        // 削除対象のファイルを取得
        List<File> fileList = getFileList(path);
        fileList.stream().filter(File::isFile).forEach(e -> e.delete());

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
        if (FileSeparator.WINDOWS == sep) {
            return convertPathFile(srcPath, FileSeparator.LINUX, FileSeparator.WINDOWS);
        } else if (FileSeparator.LINUX == sep) {
            return convertPathFile(srcPath, FileSeparator.WINDOWS, FileSeparator.LINUX);
        } else {
            // WindowsでもLinuxでもない場合、そのまま返す
            return getFile(srcPath);
        }
    }

    /**
     * 指定した<code>path</code>の{@linkplain File}オブジェクトを返す
     *
     * @param path
     *     パス
     * @return File
     */
    public static File getFile(String path) {
        return getPath(path).toFile();
    }

    /**
     * 指定した<code>path</code>の{@linkplain Path}オブジェクトを返す
     *
     * @param path
     *     ファイルパス
     * @return Path
     */
    public static Path getPath(String path) {
        return Paths.get(path);
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
     * @throws BaseException
     *     ディレクトリの作成に失敗した場合
     */
    public static void mkdir(String path) throws BaseException {

        try {
            LOG.info("path=" + path + "を作成");
            Files.createDirectories(getPath(path));
        } catch (IOException e) {
            throw new SystemException(CommonErrorCode.FILE_OR_DIR_ERROR,
                    "ディレクトリの作成に失敗しました. path=" + path, e);
        }
    }

    /**
     * 指定された元ファイルパス<code>srcPath</code>を<br>
     * 指定された<code>from</code>のパスから指定された<code>to</code>のパスに変換する
     *
     * @param srcPath
     *     元ファイルパス
     * @param from
     *     ファイルセパレータ
     * @param to
     *     ファイルセパレータ
     * @return パス変換後のファイルオブジェクト
     * @see FileUtil#convertPathFile(String, FileSeparator)
     */
    private static File convertPathFile(String srcPath, FileSeparator from,
            FileSeparator to) {
        if (srcPath.contains(to.getValue())) {
            return getFile(srcPath);
        } else {
            return getFile(srcPath.replaceAll(from.getValue(), to.getValue()));
        }
    }

    /**
     * ファイル拡張子の列挙
     *
     * @version 1.0.0
     */
    public static enum FileExtension implements BaseEnum {

        /** csv */
        CSV(".csv"),
        /** zip */
        ZIP(".zip"),
        /** java */
        JAVA(".java"),
        /** sql */
        SQL(".sql"),
        /** text */
        TEXT(".text");

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

        @Override
        public String getValue() {
            return this.value;
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
     *
     * @version 1.0.0
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

        @Override
        public String getValue() {
            return this.value;
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

    /**
     * 改行コードの列挙
     *
     * @version 1.0.0
     */
    public static enum LineFeedType implements BaseEnum {

        /** CR */
        CR("\r"),
        /** LF */
        LF("\n"),
        /** CRLF */
        CRLF("\r\n");

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private LineFeedType(String value) {
            this.value = value;
        }

        /** 値 */
        private String value;

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return LineFeedType
         */
        public static LineFeedType of(String value) {
            return BaseEnum.of(LineFeedType.class, value);
        }

    }

}
