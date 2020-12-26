package jp.co.ha.business.api.node.type;

import jp.co.ha.business.api.type.ApiNameType;
import jp.co.ha.common.type.BaseEnum;

/**
 * API種別<br>
 * <ul>
 * <li>{@linkplain #BASIC}：基礎健康情報計算API</li>
 * <li>{@linkplain #CALORIE}：カロリー計算API</li>
 * <li>{@linkplain #BREATHING_CAPACITY}：肺活量計算API</li>
 * <li>{@linkplain #TOKEN}：トークン発行API</li>
 * </ul>
 *
 * @version 1.0.0
 */
public enum NodeApiType implements BaseEnum {

    /** 基礎健康情報計算API */
    BASIC("basic", ApiNameType.BASIC),
    /** カロリー計算API */
    CALORIE("calorie", ApiNameType.CALORIE),
    /** 肺活量計算API */
    BREATHING_CAPACITY("breathing_capacity", ApiNameType.BREATHING_CAPACITY),
    /** トークン発行API */
    TOKEN("token", ApiNameType.TOKEN);

    /** 値 */
    private String value;
    /** API名 */
    private ApiNameType apiNameType;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     * @param apiNameType
     *     API名
     */
    private NodeApiType(String value, ApiNameType apiNameType) {
        this.value = value;
        this.apiNameType = apiNameType;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * apiNameTypeを返す
     *
     * @return apiNameType
     */
    public ApiNameType getApiNameType() {
        return apiNameType;
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
