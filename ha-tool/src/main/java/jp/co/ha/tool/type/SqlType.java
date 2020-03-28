package jp.co.ha.tool.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * SQLの列挙
 *
 * @version 1.0.0
 */
public enum SqlType implements BaseEnum {

    /** select */
    SELECT("select"),
    /** update */
    UPDATE("update"),
    /** insert */
    INSERT("insert"),
    /** delete */
    DELETE("delete");

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private SqlType(String value) {
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
     * @return SqlType
     */
    public static SqlType of(String value) {
        return BaseEnum.of(SqlType.class, value);
    }
}
