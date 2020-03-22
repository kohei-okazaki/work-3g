package jp.co.ha.common.io.file.csv;

import jp.co.ha.common.type.BaseEnum;

/**
 * CSVファイル囲い文字列挙
 *
 * @since 1.0
 */
public enum CsvFileChar implements BaseEnum {

    /** シングルクォート */
    SINGLE_QUOTE("\'"),
    /** ダブルクォート */
    DOBBLE_QUOTE("\"");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private CsvFileChar(String value) {
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
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return CsvFileChar
     */
    public static CsvFileChar of(String value) {
        return BaseEnum.of(CsvFileChar.class, value);
    }
}
