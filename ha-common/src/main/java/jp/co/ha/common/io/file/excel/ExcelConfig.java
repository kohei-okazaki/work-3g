package jp.co.ha.common.io.file.excel;

import jp.co.ha.common.function.Builder;
import jp.co.ha.common.type.Charset;

/**
 * Excel設定情報保持クラス<br>
 * 今後、追加するExcel設定情報は本クラスで定義する<br>
 *
 * @version 1.0.0
 */
public class ExcelConfig {

    /** ファイル名 */
    private final String fileName;
    /** 文字コード */
    private final Charset charset;
    /** ヘッダ有無 */
    private final boolean hasHeader;
    /** フッタ有無 */
    private final boolean hasFooter;
    /** マスク利用有無 */
    private final boolean useMask;

    /**
     * ExcelConfigのビルダー
     *
     * @version 1.0.0
     */
    public static class ExcelConfigBuilder implements Builder<ExcelConfig> {

        /* 必須項目 */
        /** ファイル名 */
        private final String fileName;

        /* 任意項目 */
        /** 文字コード */
        private Charset charset = Charset.UTF_8;
        /** ヘッダ有無 */
        private boolean hasHeader = false;
        /** フッタ有無 */
        private boolean hasFooter = false;
        /** マスク利用有無 */
        private boolean useMask = false;

        /**
         * コンストラクタ
         *
         * @param fileName
         *     ファイル名
         */
        public ExcelConfigBuilder(String fileName) {
            this.fileName = fileName;
        }

        /**
         * 文字コードを設定
         *
         * @param charset
         *     文字コード
         * @return ExcelConfigBuilder
         */
        public ExcelConfigBuilder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        /**
         * ヘッダ有無を設定
         *
         * @param hasHeader
         *     ヘッダ有無
         * @return ExcelConfigBuilder
         */
        public ExcelConfigBuilder hasHeader(boolean hasHeader) {
            this.hasHeader = hasHeader;
            return this;
        }

        /**
         * フッタ有無を設定
         *
         * @param hasFooter
         *     フッタ有無
         * @return ExcelConfigBuilder
         */
        public ExcelConfigBuilder hasFooter(boolean hasFooter) {
            this.hasFooter = hasFooter;
            return this;
        }

        /**
         * マスク利用有無を設定
         *
         * @param useMask
         *     マスク利用有無
         * @return ExcelConfigBuilder
         */
        public ExcelConfigBuilder useMask(boolean useMask) {
            this.useMask = useMask;
            return this;
        }

        @Override
        public ExcelConfig build() {
            return new ExcelConfig(this);
        }

    }

    /**
     * プライベートコンストラクタ<br>
     * ビルダーからのみインスタンスの生成を行うためprivateにする
     *
     * @param builder
     *     ExcelConfigのビルダー
     */
    private ExcelConfig(ExcelConfigBuilder builder) {
        this.fileName = builder.fileName;
        this.charset = builder.charset;
        this.hasHeader = builder.hasHeader;
        this.hasFooter = builder.hasFooter;
        this.useMask = builder.useMask;
    }

    /**
     * fileNameを返す
     *
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * charsetを返す
     *
     * @return charset Charset
     */
    public Charset getCharsetType() {
        return charset;
    }

    /**
     * hasHeaderを返す
     *
     * @return hasHeader
     */
    public boolean hasHeader() {
        return hasHeader;
    }

    /**
     * hasFooterを返す
     *
     * @return hasFooter
     */
    public boolean hasFooter() {
        return hasFooter;
    }

    /**
     * useMaskを返す
     *
     * @return useMask
     */
    public boolean useMask() {
        return useMask;
    }

}
