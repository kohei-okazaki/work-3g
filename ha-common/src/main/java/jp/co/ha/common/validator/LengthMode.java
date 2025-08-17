package jp.co.ha.common.validator;

import jp.co.ha.common.type.BaseEnum;

/**
 * 桁数チェック時のモードの列挙体
 * 
 * @version 1.0.0
 */
public enum LengthMode implements BaseEnum {

    /** 一致のみ */
    EQUAL(0),
    /** 以下 */
    LESS_EQUAL(1),
    /** 以上 */
    GREATER_EQUAL(2),
    /** 未満 */
    LESS_THAN(3),
    /** より大きい */
    GREATER_THAN(4);

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private LengthMode(int value) {
        this.value = value;
    }

    /** 値 */
    private int value;

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return ClassType
     */
    public static LengthMode of(String value) {
        return BaseEnum.of(LengthMode.class, value);
    }
}
