package jp.co.ha.tool.excel.type;

import java.math.BigDecimal;
import java.util.Date;

import jp.co.ha.common.type.BaseEnum;

/**
 * カラム定義の列挙
 *
 * @version 1.0.0
 */
public enum ColumnType implements BaseEnum {

    /** VARCHAR */
    VARCHAR("VARCHAR", String.class),
    /** DATE */
    DATE("DATE", Date.class),
    /** TIMESTAMP */
    DATETIME("DATETIME", Date.class),
    /** DOUBLE */
    DECIMAL("DECIMAL", BigDecimal.class),
    /** INT */
    INT("INT", BigDecimal.class);

    /** 値 */
    private String value;
    /** クラス型 */
    private Class<?> classType;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     * @param classType
     *     クラス型
     */
    private ColumnType(String value, Class<?> classType) {
        this.value = value;
        this.classType = classType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * classTypeを返す
     *
     * @return classType
     */
    public Class<?> getClassType() {
        return classType;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return ColumnType
     */
    public static ColumnType of(String value) {
        return BaseEnum.of(ColumnType.class, value);
    }
}
