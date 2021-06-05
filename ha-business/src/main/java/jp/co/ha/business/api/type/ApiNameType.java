package jp.co.ha.business.api.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * API名の列挙体
 *
 * @version 1.0.0
 */
public enum ApiNameType implements BaseEnum {

    /** 健康情報登録API */
    HEALTH_INFO_REGIST("健康管理API:健康情報登録API"),
    /** 健康情報照会API */
    HEALTH_INFO_REFERENCE("健康管理API:健康情報照会API"),
    /** ヘルスチェックAPI */
    HEALTH_CHECK("健康管理API:ヘルスチェックAPI"),
    /** 計算API:ヘルスチェックAPI */
    NODE_API_HEALTH_CHECK("Node API:ヘルスチェックAPI"),
    /** 計算API:トークン発行API */
    NODE_API_TOKEN("Node API:トークン発行API"),
    /** 計算API:基礎健康情報計算API */
    NODE_API_BASIC("Node API:基礎健康情報計算API"),
    /** 計算API:カロリー計算API */
    NODE_API_CALORIE("Node API:カロリー計算API"),
    /** 計算API: 肺活量計算API */
    NODE_API_BREATHING_CAPACITY("Node API:肺活量計算API"),
    /** 管理者用API:ヘルスチェックAPI */
    ROOT_API_HEALTH_CHECK("Root API:ヘルスチェックAPI");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private ApiNameType(String value) {
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
     * @return ApiNameType
     */
    public static ApiNameType of(String value) {
        return BaseEnum.of(ApiNameType.class, value);
    }

}
