package jp.co.ha.tool.source.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * クラス型の列挙
 *
 * @version 1.0.0
 */
public enum ClassType implements BaseEnum {

    /** クラス */
    CLASS("class"),
    /** インターフェース */
    INTERFACE("interface"),
    /** アノテーション */
    ANNOTATION("@interface"),
    /** 列挙 */
    ENUM("enum");

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private ClassType(String value) {
        this.value = value;
    }

    /** 値 */
    private String value;

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return ClassType
     */
    public static ClassType of(String value) {
        return BaseEnum.of(ClassType.class, value);
    }
}
