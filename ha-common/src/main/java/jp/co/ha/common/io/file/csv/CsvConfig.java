package jp.co.ha.common.io.file.csv;

import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.Builder;

/**
 * CSV設定情報保持クラス<br>
 * 今後、追加するCSV設定情報は本クラスで定義する
 *
 * @version 1.0.0
 */
public class CsvConfig {

    /** ファイル名 */
    private final String FILE_NAME;
    /** ファイル出力パス */
    private final String OUTPUT_PATH;
    /** ヘッダ有無 */
    private final boolean HAS_HEADER;
    /** 囲い文字有無 */
    private final boolean HAS_ENCLOSURE;
    /** 囲い文字 */
    private final CsvFileChar CSV_FILE_CHAR;
    /** 文字コード */
    private final Charset CHARSET;
    /** フッタ有無 */
    private final boolean HAS_FOOTER;
    /** マスク利用有無 */
    private final boolean USE_MASK;

    /**
     * CsvConfigのビルダー
     *
     * @version 1.0.0
     */
    public static class CsvConfigBuilder implements Builder<CsvConfig> {

        /* 必須項目 */
        /** ファイル名 */
        private final String FILE_NAME;
        /** ファイル出力パス */
        private final String OUTPUT_PATH;

        /* 任意項目 */
        /** ヘッダ有無 */
        private boolean hasHeader = false;
        /** 囲い文字有無 */
        private boolean hasEnclosure = false;
        /** 囲い文字 */
        private CsvFileChar csvFileChar = CsvFileChar.DOBBLE_QUOTE;
        /** 文字コード */
        private Charset charset = Charset.UTF_8;
        /** フッタ有無 */
        private boolean hasFooter = false;
        /** マスク利用有無 */
        private boolean useMask = false;

        /**
         * コンストラクタ
         *
         * @param fileName
         *     ファイル名
         * @param outputPath
         *     ファイル出力パス
         */
        public CsvConfigBuilder(String fileName, String outputPath) {
            this.FILE_NAME = fileName;
            this.OUTPUT_PATH = outputPath;
        }

        /**
         * ヘッダ有無を設定
         *
         * @param hasHeader
         *     ヘッダ有無
         * @return CsvConfigBuilder
         */
        public CsvConfigBuilder hasHeader(boolean hasHeader) {
            this.hasHeader = hasHeader;
            return this;
        }

        /**
         * 囲い文字有無を設定
         *
         * @param hasEnclosure
         *     囲い文字有無
         * @return CsvConfigBuilder
         */
        public CsvConfigBuilder hasEnclosure(boolean hasEnclosure) {
            this.hasEnclosure = hasEnclosure;
            return this;
        }

        /**
         * 囲い文字を設定
         *
         * @param csvFileChar
         *     囲い文字
         * @return CsvConfigBuilder
         */
        public CsvConfigBuilder csvFileChar(CsvFileChar csvFileChar) {
            this.csvFileChar = csvFileChar;
            return this;
        }

        /**
         * 文字コードを設定
         *
         * @param charset
         *     文字コード
         * @return CsvConfigBuilder
         */
        public CsvConfigBuilder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        /**
         * フッタ有無を設定
         *
         * @param hasFooter
         *     フッタ有無
         * @return CsvConfigBuilder
         */
        public CsvConfigBuilder hasFooter(boolean hasFooter) {
            this.hasFooter = hasFooter;
            return this;
        }

        /**
         * マスク利用有無を設定
         *
         * @param useMask
         *     マスク利用有無
         * @return CsvConfigBuilder
         */
        public CsvConfigBuilder useMask(boolean useMask) {
            this.useMask = useMask;
            return this;
        }

        @Override
        public CsvConfig build() {
            return new CsvConfig(this);
        }

    }

    /**
     * コンストラクタ<br>
     * ビルダーからのみインスタンスの生成を行うためprivateにする
     *
     * @param builder
     *     CsvConfigのビルダー
     */
    private CsvConfig(CsvConfigBuilder builder) {
        this.FILE_NAME = builder.FILE_NAME;
        this.OUTPUT_PATH = builder.OUTPUT_PATH;
        this.HAS_HEADER = builder.hasHeader;
        this.HAS_ENCLOSURE = builder.hasEnclosure;
        this.CSV_FILE_CHAR = builder.csvFileChar;
        this.CHARSET = builder.charset;
        this.HAS_FOOTER = builder.hasFooter;
        this.USE_MASK = builder.useMask;
    }

    /**
     * fileNaファイル名meを返す
     *
     * @return fileName
     */
    public String getFileName() {
        return FILE_NAME;
    }

    /**
     * ファイル出力パスを返す
     *
     * @return outputPath
     */
    public String getOutputPath() {
        return OUTPUT_PATH;
    }

    /**
     * ヘッダ有無を返す
     *
     * @return hasHeader
     */
    public boolean hasHeader() {
        return HAS_HEADER;
    }

    /**
     * 囲い文字有無を返す
     *
     * @return hasEnclosure
     */
    public boolean hasEnclosure() {
        return HAS_ENCLOSURE;
    }

    /**
     * 囲い文字を返す
     *
     * @return csvFileChar
     */
    public CsvFileChar getCsvFileChar() {
        return CSV_FILE_CHAR;
    }

    /**
     * 文字コードを返す
     *
     * @return charset
     */
    public Charset getCharset() {
        return CHARSET;
    }

    /**
     * フッタ有無を返す
     *
     * @return hasFooter
     */
    public boolean hasFooter() {
        return HAS_FOOTER;
    }

    /**
     * マスク利用有無を返す
     *
     * @return useMask
     */
    public boolean useMask() {
        return USE_MASK;
    }

}
