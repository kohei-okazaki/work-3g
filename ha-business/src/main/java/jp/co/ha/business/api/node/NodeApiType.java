package jp.co.ha.business.api.node;

import jp.co.ha.common.type.BaseEnum;

/**
 * API種別<br>
 * <ul>
 * <li>BASIC：基礎健康情報計算API</li>
 * <li>CALORIE：カロリー計算API</li>
 * <li>BREATHING_CAPACITY：肺活量計算API</li>
 * </ul>
 *
 * @version 1.0.0
 */
public enum NodeApiType implements BaseEnum {

    /** 基礎健康情報計算API */
    BASIC("basic", "基礎健康情報計算API"),
    /** カロリー計算API */
    CALORIE("calorie", "カロリー計算API"),
    /** 肺活量計算API */
    BREATHING_CAPACITY("breathing_capacity", "肺活量計算API");

    /** 値 */
    private String value;
    /** API名 */
    private String name;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     * @param name
     *     名前
     */
    private NodeApiType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * nameを返す
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return NodeApiType
     */
    public static NodeApiType of(String value) {
        return BaseEnum.of(NodeApiType.class, value);
    }
}
