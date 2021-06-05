package jp.co.ha.business.api.root.type;

import jp.co.ha.business.api.type.ApiNameType;
import jp.co.ha.common.type.BaseEnum;

/**
 * 管理者用API種別
 * <ul>
 * <li>{@linkplain #HEALTH_CHECK}：基礎健康情報計算API</li>
 * </ul>
 *
 * @version 1.0.0
 */
public enum RootApiType implements BaseEnum {

    /** ヘルスチェックAPI */
    HEALTH_CHECK("healthcheck", ApiNameType.ROOT_API_HEALTH_CHECK);

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
    private RootApiType(String value, ApiNameType apiNameType) {
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
    public static RootApiType of(String value) {
        return BaseEnum.of(RootApiType.class, value);
    }

}
