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

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;

/**
 * ファイル操作のUtilクラス
 *
 * @since 1.0
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
                Charset.forName("UTF-8"))) {
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
            LOG.error("Zipへの圧縮に失敗しました", e);
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
     */
    public static void copyFile(File srcFile, File destFile) {
        try (FileChannel srcChannel = new FileInputStream(srcFile).getChannel();
                FileChannel destChannel = new FileOutputStream(destFile).getChannel()) {
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
        } catch (FileNotFoundException e) {
            LOG.error("ファイルが見つかりません srcFile:" + srcFile.getPath() + " destFile:"
                    + destFile.getPath(), e);
        } catch (IOException e) {
            LOG.error("ファイルの書き込みや読み込みに失敗しました", e);
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
        try {
            Path src = Paths.get(srcPath);
            Path dest = Paths.get(destPath);
            Files.deleteIfExists(dest);
            Files.copy(src, dest);
        } catch (IOException e) {
            LOG.error("ファイルのコピーに失敗しました srcFilePath:" + srcPath + " destFilePath:"
                    + destPath, e);
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

        for (File file : fileList) {
            if (file.isFile()) {
                file.delete();
            }
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
                return getFile(srcPath.replaceAll(FileSeparator.LINUX.getValue(),
                        FileSeparator.WINDOWS.getValue()));
            }
        } else {
            if (srcPath.contains(FileSeparator.LINUX.getValue())) {
                return getFile(srcPath);
            } else {
                return getFile(srcPath.replaceAll(FileSeparator.WINDOWS.getValue(),
                        FileSeparator.LINUX.getValue()));
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
     *
     * @since 1.0
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
     *
     * @since 1.0
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
     * @since 1.0
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

        /**
         * {@inheritDoc}
         */
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
