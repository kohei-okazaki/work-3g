package jp.co.ha.tool.excel;

import jp.co.ha.tool.excel.type.ColumnType;

/**
 * 自動生成ツールのCellクラス
 *
 * @version 1.0.0
 */
public class ExcelCell {

    /** 値 */
    private String value;
    /** クラス型 */
    private Class<?> clazz;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    public ExcelCell(String value) {
        this.value = value;
    }

    /**
     * コンストラクタ
     * 
     * @param value
     *     値
     * @param clazz
     *     クラス型
     */
    public ExcelCell(String value, Class<?> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    /**
     * 値を返す
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * クラス型を返す
     * 
     * @return clazz
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * ColumnTypeを返す
     *
     * @return value
     */
    public ColumnType getColumnType() {
        return ColumnType.of(this.value);
    }
}
