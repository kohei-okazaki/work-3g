package jp.co.ha.common.type;

/**
 * フラグ値列挙
 *
 * @version 1.0.0
 */
public enum CommonFlag implements BaseEnum {

    /** 真 */
    TRUE("1", true),
    /** 偽 */
    FALSE("0", false);

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private CommonFlag(String value, boolean bool) {
        this.value = value;
        this.bool = bool;
    }

    /** 値 */
    private String value;
    /** 真偽値 */
    private boolean bool;

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * boolを返す
     * 
     * @return bool
     */
    public boolean get() {
        return bool;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return CommonFlag
     */
    public static CommonFlag of(String value) {
        return BaseEnum.of(CommonFlag.class, value);
    }
}
