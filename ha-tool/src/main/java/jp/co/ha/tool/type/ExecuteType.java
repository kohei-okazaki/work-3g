package jp.co.ha.tool.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * SqlExecutorの列挙
 *
 * @version 1.0.0
 */
public enum ExecuteType implements BaseEnum {

    /** entity作成 */
    ENTITY("ENTITY"),
    /** DDL作成 */
    DDL("DDL"),
    /** DML作成 */
    DML("DML"),
    /** DROP作成 */
    DROP("DROP"),
    /** TABLE_DEFINE */
    TABLE_DEFINE("TABLE_DEFINE");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private ExecuteType(String value) {
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
     * @return ExecuteType
     */
    public static ExecuteType of(String value) {
        return BaseEnum.of(ExecuteType.class, value);
    }
}
