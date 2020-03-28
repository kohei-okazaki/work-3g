package jp.co.ha.business.healthInfo.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * 性別の列挙
 *
 * @version 1.0.0
 */
public enum GenderType implements BaseEnum {

    /** 男性 */
    MALE("0"),
    /** 女性 */
    FEMALE("1");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private GenderType(String value) {
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
     * @return GenderType
     */
    public static GenderType of(String value) {
        return BaseEnum.of(GenderType.class, value);
    }

}
